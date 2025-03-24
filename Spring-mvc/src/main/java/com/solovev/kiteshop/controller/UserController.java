package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Collection<User>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }
}
