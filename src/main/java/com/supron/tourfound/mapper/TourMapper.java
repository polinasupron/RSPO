package com.supron.tourfound.mapper;

import com.supron.tourfound.dto.TourDto;
import com.supron.tourfound.entity.TourEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TourMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    TourEntity toTourEntity(TourDto tourDto);
}
