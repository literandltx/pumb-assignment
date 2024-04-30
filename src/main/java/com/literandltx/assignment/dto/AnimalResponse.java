package com.literandltx.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.literandltx.assignment.model.Animal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalResponse {
    private String name;
    private String type;
    private Animal.Sex sex;
    private Animal.Category category;
    private Integer weight;
    private Integer cost;
}
