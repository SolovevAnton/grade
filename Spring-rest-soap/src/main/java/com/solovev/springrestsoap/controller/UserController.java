package com.solovev.springrestsoap.controller;

import com.solovev.springrestsoap.controller.hateoas.UserLinksProvider;
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
    private final UserLinksProvider userLinksProvider;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        var users = userService.getAll();
        users.forEach(userLinksProvider::addLinks);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getById(userId).orElseThrow();
        userLinksProvider.addLinks(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.save(user);
        return ResponseEntity.created(userLinksProvider.addLinks(saved).toUri()).body(saved);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
