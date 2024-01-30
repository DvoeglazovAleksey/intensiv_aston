package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthenticationUserDto;
import org.example.dto.CreateUserDto;
import org.example.dto.ResultUserDto;
import org.example.dto.UpdateUserPasswordDto;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity <List<ResultUserDto>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<ResultUserDto> authentication(@RequestParam long id, @RequestBody AuthenticationUserDto authenticationUserDto) {
        return ResponseEntity.ok(userService.getAuthentication(id, authenticationUserDto));
    }

    @PostMapping("/create")
    public ResponseEntity<ResultUserDto> create(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(userService.create(createUserDto));
    }

    @PatchMapping("/password")
    public ResponseEntity<ResultUserDto> updatePassword(@RequestParam long id, @RequestBody UpdateUserPasswordDto updateUserPasswordDto) {
        return ResponseEntity.ok(userService.updatePassword(id, updateUserPasswordDto));
    }
}
