package com.literandltx.assignment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Optional;

@Getter
@Setter
@ToString
@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    public Animal() {
    }

    public Animal(
            final String name,
            final String type,
            final Sex sex,
            final Category category,
            final Integer weight,
            final Integer cost
    ) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.category = category;
        this.weight = weight;
        this.cost = cost;
    }

    @Getter
    public enum Sex {
        MALE("MALE"),
        FEMALE("FEMALE");

        private final String name;

        Sex(final String name) {
            this.name = name;
        }

        public static Optional<Sex> fromString(final String sexString) {
            for (final Sex sex : Sex.values()) {
                if (sex.name.equalsIgnoreCase(sexString)) {
                    return Optional.of(sex);
                }
            }

            return Optional.empty();
        }
    }

    @Getter
    public enum Category {
        FIRST_CATEGORY("FIRST_CATEGORY"),
        SECOND_CATEGORY("SECOND_CATEGORY"),
        THIRD_CATEGORY("THIRD_CATEGORY"),
        FOURTH_CATEGORY("FOURTH_CATEGORY");

        private final String name;

        Category(final String name) {
            this.name = name;
        }

        public static Optional<Category> getCategory(final Integer weight) {
            if (weight < 0) {
                return Optional.empty();
            } else if (weight <= 20) {
                return Optional.of(Category.FIRST_CATEGORY);
            } else if (weight <= 40) {
                return Optional.of(Category.SECOND_CATEGORY);
            } else if (weight <= 60) {
                return Optional.of(Category.THIRD_CATEGORY);
            } else {
                return Optional.of(Category.FOURTH_CATEGORY);
            }
        }
    }

}
