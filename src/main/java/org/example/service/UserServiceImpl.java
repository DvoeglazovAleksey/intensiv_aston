package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthenticationUserDto;
import org.example.dto.CreateUserDto;
import org.example.dto.ResultUserDto;
import org.example.dto.UpdateUserPasswordDto;
import org.example.entity.User;
import org.example.exception.ConflictException;
import org.example.exception.NotFoundException;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional()
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<ResultUserDto> getAllUsers() {
        List<User> users = repository.getAll();

        if (users.isEmpty()) {
            return new ArrayList<>();
        } else {
            return users.stream()
                    .map(UserMapper::toResultUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public ResultUserDto getAuthentication(long id, AuthenticationUserDto authenticationUserDto) {
 //     Проверяем валидные ли пришли поля пользователя.
        checkUserAuthentication(authenticationUserDto);
//      Существует ли пользователь.
        User userRepo = repository.findById(id);
        if (Objects.isNull(userRepo)) {
            throw new NotFoundException("User with id = " + id + " does not exist");
        }
//      Проверяем правильный ли логин, пароль ввел пользователь.
        checkLoginAndPassword(userRepo.getPassword(), userRepo.getLogin(),
                authenticationUserDto.getPassword(), authenticationUserDto.getLogin());
        return UserMapper.toResultUserDto(userRepo);
    }

    @Override
    @Transactional
    public ResultUserDto create(CreateUserDto createUserDto) {
//      Проверяем валидные ли поля нового пользователя.
        checkNewUser(createUserDto);
        // Проверяем нет ли в базе пользователя с таким логином который хотим создать.
        User userRepo = repository.findByLogin(createUserDto.getLogin());
        if (Objects.isNull(userRepo)) {
            User userResult = repository.addAndUpdate(UserMapper.createUserDtoToUser(createUserDto));
            return UserMapper.toResultUserDto(userResult);
        } else {
            throw new ConflictException("User with login =  " + createUserDto.getLogin() +
                    " already exists, please change your login");
        }
    }

    @Override
    public ResultUserDto updatePassword(long id, UpdateUserPasswordDto updateUserPasswordDto) {
//      Проверяем валидные ли поля пришедшего пользователя.
        checkUpdateUser(updateUserPasswordDto);
//      Проверяем наличие пользователя в бд.
        User userRepo = repository.findById(id);
        if (Objects.isNull(userRepo)) {
            throw new NotFoundException("Not user in BD");
        }
//      Проверяем логин и старый пароль введенный пользователем.
        checkLoginAndPassword(userRepo.getPassword(), userRepo.getLogin(),
                updateUserPasswordDto.getOldPassword(), updateUserPasswordDto.getLogin());
//      Присваиваем новый пароль.
        userRepo.setPassword(updateUserPasswordDto.getNewPassword());
        return UserMapper.toResultUserDto(repository.addAndUpdate(userRepo));
    }

    private void checkLoginAndPassword(String passwordUserBd, String loginBd, String passwordUser, String loginUser) {
        if (!loginBd.equals(loginUser)) {
            throw new ConflictException("Invalid login");
        }
        if (!passwordUserBd.equals(passwordUser)) {
            throw new ConflictException("Invalid password");
        }
    }

    private void checkNewUser(CreateUserDto user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("There is no data about the user");
        }
        if (Objects.isNull(user.getLogin()) || user.getLogin().isBlank()) {
            throw new IllegalArgumentException("Enter login");
        }
        if (Objects.isNull(user.getFirstName()) || user.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Enter firstName");
        }
        if (Objects.isNull(user.getLastName()) || user.getLastName().isBlank()) {
            throw new IllegalArgumentException("Enter lastName");
        }
        if (Objects.isNull(user.getPassword()) || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Enter password");
        }
    }

    private void checkUpdateUser(UpdateUserPasswordDto user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("There is no data about the user");
        }
        if (Objects.isNull(user.getLogin()) || user.getLogin().isBlank()) {
            throw new IllegalArgumentException("Enter login");
        }
        if (Objects.isNull(user.getOldPassword()) || user.getOldPassword().isBlank()) {
            throw new IllegalArgumentException("Enter oldPassword");
        }
        if (Objects.isNull(user.getNewPassword()) || user.getNewPassword().isBlank()) {
            throw new IllegalArgumentException("Enter newPassword");
        }
        //      Проверяем
        if (user.getOldPassword().equals(user.getNewPassword())) {
            throw new ConflictException("The newPassword matches the oldPassword");
        }
    }

    private void checkUserAuthentication(AuthenticationUserDto user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("There is no data about the user");
        }
        if (Objects.isNull(user.getLogin()) || user.getLogin().isBlank()) {
            throw new IllegalArgumentException("Enter login");
        }
        if (Objects.isNull(user.getPassword()) || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Enter password");
        }
    }
}
