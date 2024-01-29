package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserInfo;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserInfo> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserInfo getAuthentication(@PathVariable long id) {
        return userService.getAuthentication(id);
    }

    @PostMapping
    public UserInfo addNewUser(@RequestBody UserInfo userInfo) {
        return userService.addNewUser(userInfo);
    }

    //    Пароль стал не актуален т.к. ввели id, сделал возможность поменять пользователю имя или фамилию.
    @PatchMapping("/{id}")
    public UserInfo updateUserName(@PathVariable long id, @RequestBody UserInfo userInfo) {
        return userService.updateUserName(id, userInfo);
    }
}
