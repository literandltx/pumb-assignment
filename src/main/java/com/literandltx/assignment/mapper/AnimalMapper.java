package com.literandltx.assignment.mapper;

import com.literandltx.assignment.config.MapperConfig;
import com.literandltx.assignment.dto.AnimalResponse;
import com.literandltx.assignment.model.Animal;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AnimalMapper {
    AnimalResponse toDto(final Animal model);
}
