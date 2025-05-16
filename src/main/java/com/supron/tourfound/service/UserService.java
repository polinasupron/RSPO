package com.supron.tourfound.service;

import com.supron.tourfound.dto.UserDto;
import com.supron.tourfound.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createNewUser(UserDto userDto);

    UserEntity getUserById(Long userId);

    void updateUser(Long userId, UserDto newUserData);
}
