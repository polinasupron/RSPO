package com.supron.tourfound.service.impl;

import com.supron.tourfound.entity.TourEntity;
import com.supron.tourfound.entity.UserEntity;
import com.supron.tourfound.entity.UserRattingEntity;
import com.supron.tourfound.exception.TourNotFoundException;
import com.supron.tourfound.exception.UserTourRattingException;
import com.supron.tourfound.repository.TourRepository;
import com.supron.tourfound.repository.UserRattingRepository;
import com.supron.tourfound.service.UserTourRattingService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTourRattingServiceImpl implements UserTourRattingService {

    private final TourRepository tourRepository;
    private final UserRattingRepository userRattingRepository;

    @Override
    public void rateTour(Long tourId, UserEntity user, byte ratting) {
        TourEntity tourEntity = tourRepository.findById(tourId)
                .orElseThrow(() -> {
                    log.error("rateTourById failed, tourId = {} is not found in DB", tourId);
                    return new TourNotFoundException("Tour id = " + tourId + " is not found");
                });

        validateRateTourRatting(ratting);

        UserRattingEntity userRattingEntity = new UserRattingEntity(user, tourEntity);
        userRattingEntity.setRatting(ratting);

        log.info("Save user ratting for tour = {}, userId = {}, ratting = {}", tourEntity, user.getId(), ratting);
        log.info("test = {}", userRattingRepository.findAll());
        userRattingRepository.save(userRattingEntity);

        log.info("Calculate avg ratting for this tour");
        List<UserRattingEntity> byTourId = userRattingRepository.findByTourId(tourId);
        var avgRatting = byTourId.stream()
                .map(UserRattingEntity::getRatting)
                .mapToInt(Byte::byteValue)
                .average()
                .orElse(0.0);


        tourEntity.setRating((int)Math.round(avgRatting));
        tourRepository.save(tourEntity);
    }

    private void validateRateTourRatting(byte ratting) {
        if (ratting < 0 || ratting > 10) {
            throw new UserTourRattingException("Rating should be >=0 and <=10");

        }

    }

    @Override
    @Nullable
    public Byte findRattingByUserAndTour(Long userId, Long tourId) {
        return userRattingRepository.findByUserIdAndTourId(userId, tourId)
                .map(UserRattingEntity::getRatting)
                .orElse(null);
    }

    @Override
    public int countRatingsForTour(Long tourId) {
        return userRattingRepository.countByTourId(tourId);
    }
}
