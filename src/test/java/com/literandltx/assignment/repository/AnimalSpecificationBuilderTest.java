package com.literandltx.assignment.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.literandltx.assignment.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

class AnimalSpecificationBuilderTest {

    @Mock
    private AnimalSpecificationProvider animalSpecificationProvider;

    private AnimalSpecificationBuilder animalSpecificationBuilder;

    @BeforeEach
    void setUp() {
        animalSpecificationBuilder = new AnimalSpecificationBuilder(animalSpecificationProvider);
    }

    @Test
    void build_WithEmptyParameters_ShouldReturnEmptySpecification() {
        // Given & When
        Specification<Animal> specification = animalSpecificationBuilder.build(
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );

        // Then
        assertEquals(Specification.where(null), specification);
    }

}