package com.literandltx.assignment.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Optional;

public class AnimalTest {

    @ParameterizedTest
    @ValueSource(strings = {"male", "MALE", "Male", "mAle", "maLe", "malE", "mAlE", "MaLe"})
    public void givenRegularInputMale_Success(String input) {
        Optional<Animal.Sex> sex = Animal.Sex.fromString(input);

        Assertions.assertEquals(Animal.Sex.MALE, sex.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"female", "FEMALE", "Female", "fEmAle", "feMale", "femAle", "femaLe", "FemAle"})
    public void givenRegularInputFemale_Success(String input) {
        Optional<Animal.Sex> sex = Animal.Sex.fromString(input);

        Assertions.assertEquals(Animal.Sex.FEMALE, sex.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 20})
    public void givenWeightLessThanOrEqualTo20_ReturnsFirstCategory(int weight) {
        Optional<Animal.Category> category = Animal.Category.getCategory(weight);

        Assertions.assertTrue(category.isPresent());
        Assertions.assertEquals(Animal.Category.FIRST_CATEGORY, category.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {21, 30, 40})
    public void givenWeightLessThanOrEqualTo40_ReturnsSecondCategory(int weight) {
        Optional<Animal.Category> category = Animal.Category.getCategory(weight);

        Assertions.assertTrue(category.isPresent());
        Assertions.assertEquals(Animal.Category.SECOND_CATEGORY, category.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {41, 50, 60})
    public void givenWeightLessThanOrEqualTo60_ReturnsThirdCategory(int weight) {
        Optional<Animal.Category> category = Animal.Category.getCategory(weight);

        Assertions.assertTrue(category.isPresent());
        Assertions.assertEquals(Animal.Category.THIRD_CATEGORY, category.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {61, 70, 80})
    public void givenWeightGreaterThan60_ReturnsFourthCategory(int weight) {
        Optional<Animal.Category> category = Animal.Category.getCategory(weight);

        Assertions.assertTrue(category.isPresent());
        Assertions.assertEquals(Animal.Category.FOURTH_CATEGORY, category.get());
    }
}
