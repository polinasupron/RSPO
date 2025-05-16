package com.supron.tourfound.controller;

import com.supron.tourfound.dto.UserDto;
import com.supron.tourfound.entity.UserEntity;
import com.supron.tourfound.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;


    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal UserEntity authUser, Model model) {
        log.debug("GET getProfile, user - > {}", authUser);

        UserEntity user = userService.getUserById(authUser.getId());

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("fullName", user.getFullName());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("userTours", user.getUsersTours());

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal UserEntity user, UserDto newUserData) {
        log.debug("POST updateProfile, newUserDto - > {}", newUserData);
        userService.updateUser(user.getId(), newUserData);//Вызов метода из клаассы userService(ctr + alt + мышкой кликнуть)
        return "redirect:/profile";
    }
}
