package com.supron.tourfound.mapper;

import com.supron.tourfound.dto.UserDto;
import com.supron.tourfound.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encryptPassword")
    public abstract UserEntity toUserEntity(UserDto userDto);

    @Named("encryptPassword")
    String encryptPassword(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }
}
