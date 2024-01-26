package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.User;
import org.example.exception.exceptions.ConflictException;
import org.example.exception.exceptions.NotFoundException;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        List<User> users = repository.getAll();
        if (users.isEmpty()) {
            return new ArrayList<>();
        } else {
            return users;
        }
    }

    @Override
    public boolean getAuthentication(User user) {
        // Проверяем логин и пароль, что не пустой.
        checkLoginAndPassword(user);

        User userRepo = repository.getUser(user.getLogin());
        // Проверяем наличие пользователя в бд
        if (Objects.isNull(userRepo)) {
            throw new NotFoundException("Not user in BD");
        }
        // Проверяем пароль.
        if (user.getOldPassword().equals(userRepo.getOldPassword())) {
            return true;
        } else {
            throw new IllegalArgumentException("Invalid user password");
        }
    }

    @Override
    public User addNewUser(User user) {
        checkLoginAndPassword(user);

        User userRepo = repository.getUser(user.getLogin());
        // Проверяем есть ли уже такой пользователь в бд.
        if (userRepo != null) {
            //Выбрасываем и перехватываем в хендлере.
            throw new ConflictException("Please change the login");
        } else {
            return repository.addUser(user);
        }
    }

    @Override
    public User updatePasswordUser(User user) {
        checkLoginAndPassword(user);

        User userRepo = repository.getUser(user.getLogin());
        // Проверяем наличие пользователя в бд
        if (Objects.isNull(userRepo)) {
            throw new NotFoundException("Not user in BD");
        }
        // Проверяем пароль и обновляем.
        if (Objects.equals(user.getOldPassword(), userRepo.getOldPassword())) {
            userRepo.setOldPassword(user.getNewPassword());
            return repository.addUser(userRepo);
        } else {
            throw new IllegalArgumentException("Invalid user login or password");
        }
    }

    private void checkLoginAndPassword(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("There is no data about the user");
        }
        if (user.getLogin().isBlank()) {
            throw new IllegalArgumentException("Enter login");
        }
        if (user.getOldPassword().isBlank()) {
            throw new IllegalArgumentException("Enter password");
        }
    }
}
