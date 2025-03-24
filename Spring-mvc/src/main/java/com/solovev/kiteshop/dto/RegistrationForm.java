package com.solovev.kiteshop.dto;

import com.solovev.kiteshop.model.user.User;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String email;
    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User(username, passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

}
