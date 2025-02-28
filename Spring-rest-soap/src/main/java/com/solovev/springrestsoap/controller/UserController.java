package com.solovev.springrestsoap.controller;

import com.solovev.springrestsoap.model.User;
import com.solovev.springrestsoap.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.solovev.springrestsoap.config.APIEndpoints.USERS_REST;

@RestController
@RequiredArgsConstructor
@RequestMapping(USERS_REST)
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.of(userService.getById(userId));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.save(user));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
