package org.example.service;

import org.example.dto.AuthenticationUserDto;
import org.example.dto.CreateUserDto;
import org.example.dto.ResultUserDto;
import org.example.dto.UpdateUserPasswordDto;

import java.util.List;

public interface UserService {
    List<ResultUserDto> getAllUsers();

    ResultUserDto getAuthentication(long userId, AuthenticationUserDto authenticationUserDto);

    ResultUserDto create(CreateUserDto createUserDto);

    ResultUserDto updatePassword(long id, UpdateUserPasswordDto updateUserPasswordDto);
}
