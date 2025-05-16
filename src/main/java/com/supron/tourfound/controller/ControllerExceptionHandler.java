package com.supron.tourfound.controller;

import com.supron.tourfound.exception.CreatingTourException;
import com.supron.tourfound.exception.IncorrectPhoneException;
import com.supron.tourfound.exception.UserTourRattingException;
import com.supron.tourfound.exception.UsernameIsAlreadyExists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(UsernameIsAlreadyExists.class)
    public String handler(Exception ex, Model model) {
        return badRequestForRegistration(ex, model);
    }

    @ExceptionHandler(IncorrectPhoneException.class)
    public String handler2(Exception ex, Model model) {
        return badRequestForRegistration(ex, model);
    }

    @ExceptionHandler(CreatingTourException.class)
    public String handler3(Exception ex, Model model) {
        return badRequestForTourCreating(ex, model);
    }

    @ExceptionHandler(UserTourRattingException.class)
    public String handler4(Exception ex, Model model) {
        return badRequestForRateTour(ex, model);
    }

    private String badRequestForRegistration(Exception ex, Model model) {
        log.warn("Error registration - {}", ex.getMessage());
        model.addAttribute("exception", ex);
        return "registration";
    }

    private String badRequestForTourCreating(Exception ex, Model model) {
        log.warn("Error tour creation- {}", ex.getMessage());
        model.addAttribute("exception", ex);
        return "admin";
    }

    private String badRequestForRateTour(Exception ex, Model model) {
        log.warn("Error tour rate - {}", ex.getMessage());
        model.addAttribute("exception", ex);
        return "tours";
    }
}
