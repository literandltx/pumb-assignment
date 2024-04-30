package com.literandltx.assignment.service.animal;

import com.literandltx.assignment.dto.AnimalResponse;
import com.literandltx.assignment.dto.AnimalSearchSortRequest;
import com.literandltx.assignment.exception.custom.UnsupportedFileExtensionException;
import com.literandltx.assignment.mapper.AnimalMapper;
import com.literandltx.assignment.model.Animal;
import com.literandltx.assignment.repository.AnimalRepository;
import com.literandltx.assignment.repository.AnimalSpecificationBuilder;
import com.literandltx.assignment.repository.OrderBuilder;
import com.literandltx.assignment.service.parser.Parser;
import com.literandltx.assignment.util.FileUtil;
import lombok.RequiredArgsConstructor;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AnimalServiceV1 implements AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalSpecificationBuilder animalSpecificationBuilder;
    private final OrderBuilder animalOrderBuilder;
    private final Parser csvParser;
    private final Parser xmlParser;
    private final AnimalMapper animalMapper;

    @Override
    public ResponseEntity<Void> upload(final MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        if (multipartFile.isEmpty()) {
            throw new UnsupportedFileExtensionException("File is empty");
        }

        final List<Animal> animals;
        final File file = FileUtil.toFile(multipartFile); // unsafe

        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).endsWith(".csv")) {
            animals = csvParser.parseToAnimals(file);

            animalRepository.saveAll(animals);

            file.delete(); // log
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        if (multipartFile.getOriginalFilename().endsWith(".xml")) {
            animals = xmlParser.parseToAnimals(file);

            animalRepository.saveAll(animals);

            file.delete(); // log
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        throw new UnsupportedFileExtensionException("Invalid multipartFile format. Only .csv and .xml files are supported");
    }

    @Override
    public ResponseEntity<List<AnimalResponse>> search(
            final AnimalSearchSortRequest request
    ) {
        final Optional<List<String>> names = Optional.ofNullable(request.getNames());
        final Optional<List<String>> types = Optional.ofNullable(request.getTypes());
        final Optional<List<Animal.Sex>> genders = Optional.ofNullable(request.getSexes());
        final Optional<List<Animal.Category>> categories = Optional.ofNullable(request.getCategories());
        final Optional<List<Integer>> weights = Optional.ofNullable(request.getWeights());
        final Optional<List<Integer>> costs = Optional.ofNullable(request.getCosts());
        final Optional<List<AnimalSearchSortRequest.SortOperation>> sortOperations = Optional.ofNullable(request.getSortOperations());

        final List<Order> animalSortingBuild = animalOrderBuilder.build(sortOperations);
        final Specification<Animal> animalFilterSpecificationBuild = animalSpecificationBuilder.build(names, types, genders, categories, weights, costs);

        final List<AnimalResponse> responses = animalRepository.findAll(animalFilterSpecificationBuild, Sort.by(animalSortingBuild)).stream()
                .map(animalMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(responses);
    }

}
