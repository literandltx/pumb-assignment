package com.literandltx.assignment.repository;

import com.literandltx.assignment.dto.AnimalSearchSortRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface OrderBuilder {
    List<Sort.Order> build(final Optional<List<AnimalSearchSortRequest.SortOperation>> sortOperations);
}
