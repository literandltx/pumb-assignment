package com.literandltx.assignment.repository;

import com.literandltx.assignment.model.Animal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface SpecificationBuilder<T> {
    Specification<T> build(
            final List<String> names,
            final List<String> types,
            final List<Animal.Sex> genders,
            final List<Animal.Category> categories,
            final List<Integer> weights,
            final List<Integer> costs
    );
}
