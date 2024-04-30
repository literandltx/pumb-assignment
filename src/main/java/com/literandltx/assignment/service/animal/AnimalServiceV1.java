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

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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
        validateFile(multipartFile);

        final List<Animal> animals;
        final File file = FileUtil.toFile(multipartFile);

        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).endsWith(".csv")) {
            animals = csvParser.parseToAnimals(file);

            animalRepository.saveAll(animals);

            deleteFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        if (multipartFile.getOriginalFilename().endsWith(".xml")) {
            animals = xmlParser.parseToAnimals(file);

            animalRepository.saveAll(animals);

            deleteFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        throw new UnsupportedFileExtensionException("Invalid multipartFile format. Only .csv and .xml files are supported");
    }

    @Override
    public ResponseEntity<List<AnimalResponse>> search(
            final AnimalSearchSortRequest request
    ) {
        final List<String> names = request.getNames();
        final List<String> types = request.getTypes();
        final List<Animal.Sex> genders = request.getSexes();
        final List<Animal.Category> categories = request.getCategories();
        final List<Integer> weights = request.getWeights();
        final List<Integer> costs = request.getCosts();
        final List<AnimalSearchSortRequest.SortOperation> sortOperations = request.getSortOperations();

        final List<Order> animalSortingBuild = animalOrderBuilder.build(sortOperations);
        final Specification<Animal> animalFilterSpecificationBuild = animalSpecificationBuilder.build(names, types, genders, categories, weights, costs);

        final List<AnimalResponse> responses = animalRepository.findAll(animalFilterSpecificationBuild, Sort.by(animalSortingBuild)).stream()
                .map(animalMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(responses);
    }

    private void validateFile(final MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        final String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".csv") && !fileName.endsWith(".xml"))) {
            throw new UnsupportedFileExtensionException("Invalid file format. Only .csv and .xml files are supported");
        }
    }

    private void deleteFile(final File file) {
        if (file != null && file.exists()) {
            if (!file.delete()) {
                log.info("Cannot delete file {}", file.getAbsolutePath());
            }
        }
    }

}
