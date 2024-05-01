package com.literandltx.assignment.service.parser;

import com.literandltx.assignment.exception.custom.FileParseException;
import com.literandltx.assignment.model.Animal;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CsvParser implements Parser {
    private static final int NAME_INDEX = 0;
    private static final int TYPE_INDEX = 1;
    private static final int SEX_INDEX = 2;
    private static final int WEIGHT_INDEX = 3;
    private static final int COST_INDEX = 4;
    private static final int SKIPPED_ROWS = 1;

    @Override
    public List<Animal> parseToAnimals(final File file) {
        final List<Animal> animals = new ArrayList<>();

        try (final Reader reader = new FileReader(file)) {
            final CSVReader csvReader = new CSVReader(reader);
            csvReader.skip(SKIPPED_ROWS);
            String[] nextLine;

            while ((nextLine = csvReader.readNext()) != null) {
                final Optional<Animal> animal = parserCsvLine(nextLine);

                animal.ifPresent(animals::add);
            }

        } catch (final IOException | CsvValidationException e) {
            throw new FileParseException("Cannot parse .csv file: " + file.getName(), e);
        }

        return animals;
    }

    protected Optional<Animal> parserCsvLine(final String[] line) throws NumberFormatException {
        if (line[NAME_INDEX].isEmpty()
                || line[TYPE_INDEX].isEmpty()
                || line[SEX_INDEX].isEmpty()
                || line[WEIGHT_INDEX].isEmpty()
                || line[COST_INDEX].isEmpty()
        ) {
            return Optional.empty();
        }

        final String name = line[NAME_INDEX];
        final String type = line[TYPE_INDEX];
        final Optional<Animal.Sex> sex = Animal.Sex.fromString(line[SEX_INDEX]);
        final Integer weight = Integer.valueOf(line[WEIGHT_INDEX].trim());
        final Integer cost = Integer.valueOf(line[COST_INDEX].trim());
        final Optional<Animal.Category> category = Animal.Category.getCategory(weight);

        if (sex.isEmpty() || category.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Animal(name, type, sex.get(), category.get(), weight, cost));
    }
}
