package com.supron.tourfound.service;

import com.supron.tourfound.dto.TourDto;
import com.supron.tourfound.entity.TourEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface TourService {

    void createNewTour(TourDto tourDto);

    List<TourEntity> findTourEntity(Map<String, String> searchParams);

    TourEntity findById(Long tourId);

    <T> Set<String> getAggregateValuesByField(List<TourEntity> tourEntities, Function<TourEntity, T> mapper);


}
