package com.literandltx.assignment.service.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.literandltx.assignment.model.Animal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Optional;
import java.util.stream.Stream;

class CsvParserValidationTest {
    private final CsvParser csvParser = new CsvParser();

    @ParameterizedTest
    @MethodSource("invalidInput_emptyCell_dataProvider")
    public void invalidInput_emptyCell(String name, String type, String sex, String weight, String cost) {
        // Given
        String[] line = new String[] {name, type, sex, weight, cost};

        // When
        Optional<Animal> animal = csvParser.parserCsvLine(line);

        // Then
        Assertions.assertEquals(Optional.empty(), animal);
    }

    @ParameterizedTest
    @MethodSource("invalidInput_integerParse_dataProvider")
    public void invalidInput_integerParse(String name, String type, String sex, String weight, String cost) {
        // Given
        String[] line = new String[] {name, type, sex, weight, cost};
        String expectedMessage = "For input string";

        // When
        Exception exception = assertThrows(NumberFormatException.class, () -> csvParser.parserCsvLine(line));
        String actualMessage = exception.getMessage();

        // Then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @MethodSource("invalidInput_invalidSexCategory_dataProvider")
    public void invalidInput_invalidSexCategory(String name, String type, String sex, String weight, String cost) {
        // Given
        String[] line = new String[] {name, type, sex, weight, cost};

        // When
        Optional<Animal> animal = csvParser.parserCsvLine(line);

        // Then
        Assertions.assertEquals(Optional.empty(), animal);
    }

    @ParameterizedTest
    @MethodSource("validInput_valid_dataProvider")
    public void validInput_valid(String name, String type, String sex, String weight, String cost) {
        // Given
        String[] line = new String[] {name, type, sex, weight, cost};
        Animal.Sex expectedSex = Animal.Sex.fromString(sex).orElseThrow();
        Animal.Category expectedCategory = Animal.Category.getCategory(Integer.parseInt(weight)).orElseThrow();
        Animal expected = new Animal(name, type, expectedSex, expectedCategory, Integer.parseInt(weight), Integer.parseInt(cost));

        // When
        Animal actual = csvParser.parserCsvLine(line).orElseThrow();

        // Then
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getType(), actual.getType());
        Assertions.assertEquals(expected.getSex(), actual.getSex());
        Assertions.assertEquals(expected.getCategory(), actual.getCategory());
        Assertions.assertEquals(expected.getWeight(), actual.getWeight());
        Assertions.assertEquals(expected.getCost(), actual.getCost());

    }

    private static Stream<Arguments> validInput_valid_dataProvider() {
        return Stream.of(
                Arguments.of("name", "type", "male", "19", "125"),
                Arguments.of("name", "type", "male", "25", "35"),
                Arguments.of("name", "type", "male", "48", "56"),
                Arguments.of("name", "type", "male", "66", "56"),

                Arguments.of("name", "type", "female", "19", "125"),
                Arguments.of("name", "type", "female", "25", "35"),
                Arguments.of("name", "type", "female", "48", "56"),
                Arguments.of("name", "type", "female", "66", "56")

        );
    }

    private static Stream<Arguments> invalidInput_invalidSexCategory_dataProvider() {
        return Stream.of(
                Arguments.of("name", "type", "other", "30", "30"),
                Arguments.of("name", "type", "male", "-1", "30"),
                Arguments.of("name", "type", "other", "-1", "30")
        );
    }

    private static Stream<Arguments> invalidInput_integerParse_dataProvider() {
        return Stream.of(
                Arguments.of("name", "type", "male", "number", "30"),
                Arguments.of("name", "type", "male", "30", "number"),
                Arguments.of("name", "type", "male", "number", "number")
        );
    }

    private static Stream<Arguments> invalidInput_emptyCell_dataProvider() {
        return Stream.of(
                Arguments.of("", "", "", "", ""),
                Arguments.of("name", "", "", "", ""),
                Arguments.of("", "type", "", "", ""),
                Arguments.of("", "", "sex", "", ""),
                Arguments.of("", "", "", "30", ""),
                Arguments.of("", "", "", "", "30"),

                Arguments.of("", "type", "male", "30", "30"),
                Arguments.of("name", "", "male", "30", "30"),
                Arguments.of("name", "type", "", "30", "30"),
                Arguments.of("name", "type", "male", "", "30"),
                Arguments.of("name", "type", "male", "30", "")
        );
    }
}