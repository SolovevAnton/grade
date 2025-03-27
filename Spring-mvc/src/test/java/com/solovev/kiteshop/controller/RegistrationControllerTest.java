package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.common.TemplatesNamings;
import com.solovev.kiteshop.dto.RegistrationForm;
import com.solovev.kiteshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private UserService userService;

    private ResultActions performRegistration(RegistrationForm form) throws Exception {
        return mockMvc.perform(post(APINamings.REGISTRATION)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", form.getUsername())
                .param("password", form.getPassword())
                .param("email", form.getEmail())
                .param("phoneNumber", form.getPhoneNumber()));
    }

    @Test
    void shouldReturnRegistrationPageWithErrorsWhenUsernameIsBlank() throws Exception {
        RegistrationForm form = new RegistrationForm();
        form.setUsername(""); // Invalid
        form.setPassword("SecurePass123");
        form.setEmail("valid@email.com");
        form.setPhoneNumber("+1234567890");

        performRegistration(form)
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("registrationForm", "username"))
                .andExpect(view().name(TemplatesNamings.REGISTER));
    }

    @Test
    void shouldReturnRegistrationPageWithErrorsWhenPasswordIsTooShort() throws Exception {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("validUser");
        form.setPassword("short"); // Invalid
        form.setEmail("valid@email.com");
        form.setPhoneNumber("+1234567890");

        performRegistration(form)
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("registrationForm", "password"))
                .andExpect(view().name(TemplatesNamings.REGISTER));
    }

    @Test
    void shouldReturnRegistrationPageWithErrorsWhenEmailIsInvalid() throws Exception {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("validUser");
        form.setPassword("SecurePass123");
        form.setEmail("invalid-email"); // Invalid
        form.setPhoneNumber("+1234567890");

        performRegistration(form)
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("registrationForm", "email"))
                .andExpect(view().name(TemplatesNamings.REGISTER));
    }

    @Test
    void shouldReturnRegistrationPageWithErrorsWhenPhoneNumberIsInvalid() throws Exception {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("validUser");
        form.setPassword("SecurePass123");
        form.setEmail("valid@email.com");
        form.setPhoneNumber("12345"); // Invalid

        performRegistration(form)
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("registrationForm", "phoneNumber"))
                .andExpect(view().name(TemplatesNamings.REGISTER));
    }

    @Test
    void shouldRedirectToLoginWhenAllFieldsAreValid() throws Exception {
        when(encoder.encode(any())).thenReturn("encodedPassHashStuff");

        RegistrationForm form = new RegistrationForm();
        form.setUsername("validUser");
        form.setPassword("SecurePass123");
        form.setEmail("valid@email.com");
        form.setPhoneNumber("+1234567890");

        performRegistration(form)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(APINamings.LOGIN));
    }

    @BeforeEach
    public void setup() {
        when(encoder.encode(any())).thenReturn("encodedPassHashStuff");

    }
}
