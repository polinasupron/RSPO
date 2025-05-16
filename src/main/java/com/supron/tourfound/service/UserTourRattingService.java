package com.supron.tourfound.service;

import com.supron.tourfound.entity.UserEntity;
import jakarta.annotation.Nullable;

public interface UserTourRattingService {

    void rateTour(Long tourId, UserEntity user, byte ratting);

    @Nullable
    Byte findRattingByUserAndTour(Long userId, Long tourId);

    int countRatingsForTour(Long tourId);

}
