package com.literandltx.assignment.repository;

import com.literandltx.assignment.dto.AnimalSearchSortRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimalOrderBuilder implements OrderBuilder {

    @Override
    public List<Sort.Order> build(final List<AnimalSearchSortRequest.SortOperation> sortOperations) {
        if (sortOperations == null || sortOperations.isEmpty()) {
            return new ArrayList<>();
        }

        final List<Sort.Order> orders = new ArrayList<>();

        for (final AnimalSearchSortRequest.SortOperation operation : sortOperations) {
            final Sort.Direction direction = Sort.Direction.fromString(operation.getDirection());
            final String field = operation.getField();

            final Sort.Order order = new Sort.Order(direction, field);

            orders.add(order);
        }

        return orders;
    }

}
