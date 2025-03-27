package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.common.TemplatesNamings;
import com.solovev.kiteshop.dto.RegistrationForm;
import com.solovev.kiteshop.exception.UserAlreadyExistsException;
import com.solovev.kiteshop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(APINamings.REGISTRATION)
@RequiredArgsConstructor
public class RegistrationController {
    private final PasswordEncoder encoder;
    private final UserService userService;

    @GetMapping
    public ModelAndView getPage() {
        ModelAndView modelAndView = new ModelAndView(TemplatesNamings.REGISTER);
        modelAndView.addObject("registrationForm", new RegistrationForm()); // Ensure form is in model
        return modelAndView;
    }

    @PostMapping
    public ModelAndView register(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView(TemplatesNamings.REGISTER);
            modelAndView.addObject("registrationForm", registrationForm);
            return modelAndView;
        }
        userService.save(registrationForm.toUser(encoder));
        return new ModelAndView("redirect:" + APINamings.LOGIN);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ModelAndView loginExists(UserAlreadyExistsException e) {
        ModelAndView view = new ModelAndView(TemplatesNamings.REGISTER);
        view.addObject("errorMessage", e.getMessage());
        return view;
    }
}
