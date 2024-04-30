package com.literandltx.assignment.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.literandltx.assignment.model.Animal;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;

class XmlParserTest {

    @Test
    public void testParseToAnimals_ValidData() {
        // Given
        XmlParser animalParser = new XmlParser();
        File testFile = new File("src/test/resources/static/xml/valid_data.xml");

        // When
        List<Animal> animals = animalParser.parseToAnimals(testFile);

        // Then
        assertEquals(2, animals.size());
        assertEquals("Milo", animals.get(0).getName());
        assertEquals("Buddy", animals.get(1).getName());

        assertEquals("cat", animals.get(0).getType());
        assertEquals("dog", animals.get(1).getType());

        assertEquals(Animal.Sex.MALE, animals.get(0).getSex());
        assertEquals(Animal.Sex.FEMALE, animals.get(1).getSex());

        assertEquals(Animal.Category.FIRST_CATEGORY, animals.get(0).getCategory());
        assertEquals(Animal.Category.FOURTH_CATEGORY, animals.get(1).getCategory());

        assertEquals(19, animals.get(0).getWeight());
        assertEquals(62, animals.get(1).getWeight());

        assertEquals(51, animals.get(0).getCost());
        assertEquals(34, animals.get(1).getCost());
    }

    @Test
    public void testParseToAnimals_InvalidData() {
        // Given
        XmlParser animalParser = new XmlParser();
        File testFile = new File("src/test/resources/static/xml/invalid_data.xml");

        // When
        List<Animal> animals = animalParser.parseToAnimals(testFile);

        // Given
        assertEquals(0, animals.size());
    }
}