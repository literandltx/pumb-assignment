package com.literandltx.assignment.service.parser;

import com.literandltx.assignment.model.Animal;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public interface Parser {
    List<Animal> parseToAnimals(final File file);
}
