package com.supron.tourfound.service.impl;

import com.supron.tourfound.dto.UserDto;
import com.supron.tourfound.entity.Role;
import com.supron.tourfound.entity.UserEntity;
import com.supron.tourfound.exception.IncorrectPhoneException;
import com.supron.tourfound.exception.UsernameIsAlreadyExists;
import com.supron.tourfound.mapper.UserMapper;
import com.supron.tourfound.repository.UserRepository;
import com.supron.tourfound.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void createNewUser(UserDto userDto) {
        userRepository.findByUsername(userDto.username())
                .ifPresentOrElse(user -> {
                            throw new UsernameIsAlreadyExists("User with userName = " + userDto.username() + "is already exists");
                        },
                        () -> {
                            validateUserData(userDto);

                            UserEntity user = userMapper.toUserEntity(userDto);
                            user.setRoles(Collections.singleton(Role.USER));

                            log.info("Save new user = {}", user);
                            userRepository.save(user);
                        }
                );
    }

    private void validateUserData(UserDto userDto) {
        if (!userDto.phone().matches("\\+375(17|29|33|44)\\d{7}")) {
            System.out.println("Phone incorrect");
            throw new IncorrectPhoneException("Please enter correct phone number");
            //Брось - создай новое  исключение
            //можешь менять текст
        }

    }

    @Override
    public void updateUser(Long userId, UserDto newUserData) {
        UserEntity userFromDb = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("update user failed, userId = {} is not found in DB", userId);
                    return new RuntimeException("User id = " + userId + " is not found");
                });

        String newUsername = newUserData.username();

        if (!newUsername.isBlank()) {
            Long userIdWithSameUsername = userRepository.findByUsername(newUsername)
                    .map(UserEntity::getId)
                    .orElse(null);

            if (nonNull(userIdWithSameUsername) && !userId.equals(userIdWithSameUsername)) {
                throw new UsernameIsAlreadyExists("User with userName = " + newUsername + "is already exists");
            }

            userFromDb.setUsername(newUsername);
        }

        if (!newUserData.email().isBlank()) {
            userFromDb.setEmail(newUserData.email());
        }

        if (!newUserData.phone().isBlank()) {
            userFromDb.setPhone(newUserData.phone());
        }

        if (!newUserData.fullName().isBlank()) {
            userFromDb.setFullName(newUserData.fullName());
        }

        log.info("Update user info, for user id = {}, newUserData = {}", userId, newUserData);
        userRepository.save(userFromDb);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("get user failed, userId = {} is not found in DB", userId);
                    return new RuntimeException("User id = " + userId + " is not found");
                });
    }
}
