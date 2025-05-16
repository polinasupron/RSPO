package com.supron.tourfound.service.impl;

import com.supron.tourfound.entity.TourEntity;
import com.supron.tourfound.entity.UserEntity;
import com.supron.tourfound.entity.UserTourEntity;
import com.supron.tourfound.exception.TourNotFoundException;
import com.supron.tourfound.repository.TourRepository;
import com.supron.tourfound.repository.UserTourRepository;
import com.supron.tourfound.service.UserTourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTourServiceImpl implements UserTourService {

    private final TourRepository tourRepository;
    private final UserTourRepository userTourRepository;

    @Override
    public void bookTourById(UserEntity user, Long tourId) {
        TourEntity tourEntity = tourRepository.findById(tourId)
                .orElseThrow(() -> {
                    log.error("bookTourById failed, tourId = {} is not found in DB", tourId);
                    return new TourNotFoundException("Tour id = " + tourId + " is not found");
                });

        UserTourEntity userTourEntity = new UserTourEntity(user, tourEntity);
        userTourEntity.setDateTime(LocalDateTime.now());


        log.info("Save userTour for user = {}, tour = {}", user, tourEntity);
        userTourRepository.save(userTourEntity);
    }
}
