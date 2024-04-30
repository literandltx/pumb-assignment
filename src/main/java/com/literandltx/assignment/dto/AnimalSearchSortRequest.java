package com.literandltx.assignment.dto;

import com.literandltx.assignment.model.Animal;
import lombok.Data;
import java.util.List;

@Data
public class AnimalSearchSortRequest {
    private List<String> names;
    private List<String> types;
    private List<Animal.Sex> sexes;
    private List<Animal.Category> categories;
    private List<Integer> weights;
    private List<Integer> costs;
    private List<SortOperation> sortOperations;

    @Data
    public static class SortOperation {
        private String field;
        private String direction;
    }
}
