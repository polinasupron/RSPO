package com.supron.tourfound.controller;

import com.supron.tourfound.dto.UserDto;
import com.supron.tourfound.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(UserDto userDto) {
        log.debug("Registration, newUser = {}", userDto);
        userService.createNewUser(userDto);//Вызов метода из клаассы userService(ctr + alt + мышкой кликнуть)
        return "redirect:/login";
    }
}
