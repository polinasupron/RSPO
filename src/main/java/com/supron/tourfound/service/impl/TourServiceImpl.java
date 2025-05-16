package com.supron.tourfound.service.impl;

import com.supron.tourfound.dto.TourDto;
import com.supron.tourfound.entity.TourEntity;
import com.supron.tourfound.exception.CreatingTourException;
import com.supron.tourfound.exception.TourNotFoundException;
import com.supron.tourfound.mapper.TourMapper;
import com.supron.tourfound.repository.TourRepository;
import com.supron.tourfound.repository.specification.SpecificationBuilder;
import com.supron.tourfound.service.TourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {


    private static final String FILTER_BY_COUNTRY = "filterByCountry";

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final List<SpecificationBuilder<TourEntity>> specificationBuilders;


    @Override
    public void createNewTour(TourDto tourDto) {
        validateTourData(tourDto);

        TourEntity tourEntity = tourMapper.toTourEntity(tourDto);

        log.info("Save new tour, tourEntity = {}", tourEntity);
        tourRepository.save(tourEntity);
    }


    private void validateTourData(TourDto tourDto) {
        if (isNull(tourDto.name()) || tourDto.name().isBlank()) {
            throw new CreatingTourException("Tour name is blank!");
        }

        if (tourDto.duration() <= 0) {
            throw new CreatingTourException("The duration of the tour cannot be less than zero.");
        }
    }

    @Override
    public List<TourEntity> findTourEntity(Map<String, String> searchParams) {
        Specification<TourEntity> spec = specificationBuilders.stream()
                .map(builder -> builder.build(searchParams))
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);

        if (isNull(spec)) {
            return tourRepository.findAll();
        }

        return tourRepository.findAll(spec);

    }

    @Override
    public TourEntity findById(Long tourId) {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> {
                    log.error("get tour failed, tour = {} is not found in DB", tourId);
                    return new TourNotFoundException("Tour id = " + tourId + " is not found");
                });
    }

    @Override
    public <T> Set<String> getAggregateValuesByField(List<TourEntity> tourEntities, Function<TourEntity, T> mapper) {
        Map<T, Long> collect = tourEntities.stream()
                .map(mapper)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return collect.entrySet().stream()
                .sorted(Map.Entry.<T, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .map(String::valueOf)
                .collect(Collectors.toSet());
    }


}
