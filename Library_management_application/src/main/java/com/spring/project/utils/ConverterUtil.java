package com.spring.project.utils;

import com.spring.project.dto.RegistrationDto;
import com.spring.project.entity.user.User;

public class ConverterUtil {

    public static User convertRegisterDtoToUser(RegistrationDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setPassword(dto.getPassword());
        user.setDateOfBirth(dto.getDateOfBirth());
        return user;
    }
}
