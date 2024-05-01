package com.literandltx.assignment.repository;

import com.literandltx.assignment.model.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AnimalSpecificationBuilder implements SpecificationBuilder<Animal> {
    private static final String FIELD_NAME = "name";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_SEX = "sex";
    private static final String FIELD_CATEGORY = "category";
    private static final String FIELD_WEIGHT = "weight";
    private static final String FIELD_COST = "cost";

    private final AnimalSpecificationProvider animalSpecificationProvider;

    @Override
    public Specification<Animal> build(
            final List<String> names,
            final List<String> types,
            final List<Animal.Sex> sexes,
            final List<Animal.Category> categories,
            final List<Integer> weights,
            final List<Integer> costs
    ) {
        final List<Specification<Animal>> specificationList = new ArrayList<>();

        if (names != null && !names.isEmpty()) {
            final List<String> list = names.stream()
                    .map(String::trim)
                    .toList();

            specificationList.add(animalSpecificationProvider.getListSpecification(FIELD_NAME, list));
        }

        if (types != null && !types.isEmpty()) {
            final List<String> list = types.stream()
                    .map(String::trim)
                    .toList();

            specificationList.add(animalSpecificationProvider.getListSpecification(FIELD_TYPE, list));
        }

        if (sexes != null && !sexes.isEmpty()) {
            final List<String> list = sexes.stream()
                            .map(Animal.Sex::getName)
                            .toList();

            specificationList.add(animalSpecificationProvider.getListSpecification(FIELD_SEX, list));
        }

        if (categories != null && !categories.isEmpty()) {
            final List<String> list = categories.stream()
                    .map(Animal.Category::getName)
                    .toList();

            specificationList.add(animalSpecificationProvider.getListSpecification(FIELD_CATEGORY, list));
        }

        if (weights != null && !weights.isEmpty()) {
            final Specification<Animal> specification = createRangeSpecification(weights, FIELD_WEIGHT);

            specificationList.add(specification);
        }

        if (costs != null && !costs.isEmpty()) {
            final Specification<Animal> specification = createRangeSpecification(costs, FIELD_COST);

            specificationList.add(specification);
        }

        if (specificationList.isEmpty()) {
            return Specification.where(null);
        }

        return Specification.allOf(specificationList);
    }

    private Specification<Animal> createRangeSpecification(final List<Integer> list, final String fieldName) {
        final Integer min = findMin(list);
        final Integer max = findMax(list);

        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("Min and max must be greater than zero, but was min: " + min + ", max: " + max);
        }

        if (min > max) {
            throw new IllegalArgumentException("Range must be (min, max) or (min), but was (" + min + ", " + max + ").");
        }

        System.out.println(min + " " + max);

        return animalSpecificationProvider.getRangeSpecification(fieldName, min, max);
    }

    private Integer findMin(final List<Integer> list) {
        Integer min = 0;

        if (list.size() == 1) {
            min = list.getFirst();
        }

        if (list.size() == 2) {
            min = list.getFirst();
        }

        return min;
    }

    private Integer findMax(final List<Integer> list) {
        Integer max = Integer.MAX_VALUE;

        if (list.size() == 2) {
            max = list.get(1);
        }

        return max;
    }

}
