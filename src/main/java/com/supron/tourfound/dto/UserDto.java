package com.supron.tourfound.dto;

public record UserDto(
        String username,
        String phone,
        String email,
        String fullName,
        String password
) {
};
