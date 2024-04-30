package com.literandltx.assignment.service.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.literandltx.assignment.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CsvParserTest extends CsvParser {

    private CsvParser animalParser;

    @BeforeEach
    void setUp() {
        animalParser = new CsvParser();
    }

    @Test
    void testParseToAnimals(@TempDir File tempDir) throws IOException {
        // Given
        File csvFile = createSampleCsvFile(tempDir);

        // When
        List<Animal> animals = animalParser.parseToAnimals(csvFile);

        // Then
        assertEquals(4, animals.size());
        assertEquals("name1", animals.get(0).getName());
        assertEquals("name2", animals.get(1).getName());
        assertEquals("name3", animals.get(2).getName());
        assertEquals("name4", animals.get(3).getName());

        assertEquals("cat", animals.get(0).getType());
        assertEquals("cat", animals.get(1).getType());
        assertEquals("dog", animals.get(2).getType());
        assertEquals("dog", animals.get(3).getType());

        csvFile.delete();
    }

    private File createSampleCsvFile(File directory) throws IOException {
        File csvFile = new File(directory, "sample.csv");
        try (PrintWriter writer = new PrintWriter(csvFile)) {
            writer.println("Name,Type,Sex,Weight,Cost");

            writer.println("name1,cat,male, 130, 10");
            writer.println("name2,cat,female, 30, 130");
            writer.println("name3,dog,male, 15, 99");
            writer.println("name4,dog,female, 43, 24");
        }
        return csvFile;
    }
}
