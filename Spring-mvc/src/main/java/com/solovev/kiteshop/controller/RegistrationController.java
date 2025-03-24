package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.common.TemplatesNamings;
import com.solovev.kiteshop.dto.RegistrationForm;
import com.solovev.kiteshop.exception.UserAlreadyExistsException;
import com.solovev.kiteshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(APINamings.REGISTRATION)
@RequiredArgsConstructor
public class RegistrationController {
    private final PasswordEncoder encoder;
    private final UserService userService;

    @GetMapping
    public ModelAndView getPage() {
        return new ModelAndView(TemplatesNamings.REGISTER);
    }

    @PostMapping
    public RedirectView register(RegistrationForm registrationForm) {
        userService.save(registrationForm.toUser(encoder));
        return new RedirectView(APINamings.LOGIN);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ModelAndView loginExists(UserAlreadyExistsException e) {
        ModelAndView view = new ModelAndView(TemplatesNamings.REGISTER);
        view.addObject("errorMessage", e.getMessage());
        return view;
    }
}
