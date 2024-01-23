package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    // PUT - выбрал без логики, просто свободный.
    @PutMapping
    public boolean getAuthentication(@RequestBody User user) {
        return userService.getAuthentication(user);
    }

    @PostMapping
    public User addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PatchMapping
    public User updatePasswordUser(@RequestBody User user) {
        return userService.updatePasswordUser(user);
    }
}
