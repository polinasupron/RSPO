package com.supron.tourfound.service;

import com.supron.tourfound.entity.UserEntity;

public interface UserTourService {

    void bookTourById(UserEntity user, Long tourId);
}
