package com.literandltx.assignment.repository;

import com.literandltx.assignment.model.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AnimalSpecificationBuilder implements SpecificationBuilder<Animal> {
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_GENDER = "gender";
    private static final String FIELD_CATEGORY = "category";
    private static final String FIELD_WEIGHT = "weight";
    private static final String FIELD_COST = "cost";

    private static final String MIN_VALUE_NOTATION = "min";
    private static final String MAX_VALUE_NOTATION = "max";

    private final AnimalSpecificationProvider animalSpecificationProvider;

    @Override
    public Specification<Animal> build(
            final Optional<List<String>> names,
            final Optional<List<String>> types,
            final Optional<List<Animal.Sex>> genders,
            final Optional<List<Animal.Category>> categories,
            final Optional<List<Integer>> weights,
            final Optional<List<Integer>> costs
    ) {
        final List<Specification<Animal>> specificationList = new ArrayList<>();

        if (names.isPresent()) {
            final List<String> list = names.get().stream()
                    .map(String::trim)
                    .toList();

            specificationList.add(animalSpecificationProvider.getListSpecification(FIELD_NAME, list));
        }

        if (types.isPresent()) {
            final List<String> list = types.get().stream()
                    .map(String::trim)
                    .toList();

            specificationList.add(animalSpecificationProvider.getListSpecification(FIELD_TYPE, list));
        }

        if (genders.isPresent()) {
            final List<String> list = genders.get().stream()
                            .map(Animal.Sex::getName)
                            .toList();

            specificationList.add(animalSpecificationProvider.getListSpecification(FIELD_GENDER, list));
        }

        if (categories.isPresent()) {
            final List<String> list = categories.get().stream()
                    .map(Animal.Category::getName)
                    .toList();

            specificationList.add(animalSpecificationProvider.getListSpecification(FIELD_CATEGORY, list));
        }

        if (weights.isPresent()) {
            final Map<String, Integer> minMax = getMinMax(weights.get());
            final Integer min = minMax.get(MIN_VALUE_NOTATION);
            final Integer max = minMax.get(MAX_VALUE_NOTATION);

            final Specification<Animal> specification = animalSpecificationProvider.getRangeSpecification(FIELD_WEIGHT, min, max);

            specificationList.add(specification);
        }

        if (costs.isPresent()) {
            final Map<String, Integer> minMax = getMinMax(costs.get());
            final Integer min = minMax.get(MIN_VALUE_NOTATION);
            final Integer max = minMax.get(MAX_VALUE_NOTATION);

            final Specification<Animal> specification = animalSpecificationProvider.getRangeSpecification(FIELD_COST, min, max);

            specificationList.add(specification);
        }

        if (specificationList.isEmpty()) {
            return Specification.where(null);
        }

        return Specification.allOf(specificationList);
    }

    private Map<String, Integer> getMinMax(final List<Integer> list) {
        final Map<String, Integer> mapMinMax = new HashMap<>();

        Integer min = 0;
        Integer max = Integer.MAX_VALUE;

        if (list.size() == 1) {
            min = list.getFirst();
        }

        if (list.size() == 2) {
            min = list.get(0);
            max = list.get(1);
        }

        mapMinMax.put(MIN_VALUE_NOTATION, min);
        mapMinMax.put(MAX_VALUE_NOTATION, max);

        return mapMinMax;
    }
}
