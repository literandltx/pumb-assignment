package com.literandltx.assignment.repository;

import com.literandltx.assignment.model.Animal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface SpecificationBuilder<T> {
    Specification<T> build(
            final Optional<List<String>> names,
            final Optional<List<String>> types,
            final Optional<List<Animal.Sex>> genders,
            final Optional<List<Animal.Category>> categories,
            final Optional<List<Integer>> weights,
            final Optional<List<Integer>> costs
    );
}
