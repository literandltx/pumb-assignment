package com.literandltx.assignment.repository;

import com.literandltx.assignment.model.Animal;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AnimalSpecificationProvider {
    public Specification<Animal> getListSpecification(final String fieldName, final List<String> params) {
        return (root, query, criteriaBuilder) -> {
          final CriteriaBuilder.In<String> inPredicate = criteriaBuilder.in(root.get(fieldName));
          params.forEach(inPredicate::value);

          return criteriaBuilder.and(inPredicate);
        };
    }

    public Specification<Animal> getRangeSpecification(final String fieldName, final Integer min, final Integer max) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(fieldName), min, max);
    }
}
