package com.literandltx.assignment.controller;

import com.literandltx.assignment.dto.AnimalSearchSortRequest;
import com.literandltx.assignment.service.animal.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "name", description = "description")
@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class AnimalControllerV1 {
    private final AnimalService animalServiceV1;

    @Operation(
            summary = "Search with filtering and sorting.",
            description = """
                    Names is case sensitive. \n
                    Types is case sensitive. \n
                    Sexes is not case sensitive, but only male/female allowed, others will be ignored. \n
                    Categories is no case sensitive, but only FIRST_CATEGORY/SECOND_CATEGORY/THIRD_CATEGORY/FOURTH_CATEGORY allowed, others will be ignored. \n
                    
                    Weight "weight" is have two working regime (only first two param will be parsed): \n
                        1. (min, max), two parameters
                        2. (min), one parameter
                    Cost "costs" is have two working regime (only first two param will be parsed): \n
                        1. (min, max), two parameters
                        2. (min), one parameter
                    
                    Direction asc/desc only allowed in any case.
                    """
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully selected."),
                    @ApiResponse(responseCode = "400", description = "Bad request. Invalid input parameters."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @GetMapping(
            path = "/animals",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> search(@RequestBody final AnimalSearchSortRequest request) {
        log.info(String.format("Search method with param: %s, was called", request.toString()));

        return ResponseEntity.ok().body(animalServiceV1.search(request));
    }

    @Operation(
            summary = "Upload a CSV or XML file",
            description = "Allow only one file .csv or .xml per one request."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
                    @ApiResponse(responseCode = "406", description = "Only CSV and XML files are only supported"),
                    @ApiResponse(responseCode = "500", description = "An error occurred while parsing the file.")
            }
    )
    @PostMapping(
            path = "/files/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> upload(
            @RequestPart("file") final MultipartFile file
    ) {
        log.info(String.format("File: %s, was uploaded.", file.getOriginalFilename()));

        return animalServiceV1.upload(file);
    }
}
