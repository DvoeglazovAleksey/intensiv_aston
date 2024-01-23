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
        if (userRepo != null && user.getOldPassword().equals(userRepo.getOldPassword())) {
            return true;
        } else {
            throw new NotFoundException("Not user in BD");
        }
    }

    @Override
    public User addNewUser(User user) {
        checkLoginAndPassword(user);

        User userRepo = repository.getUser(user.getLogin());
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

        if (user.getLogin().isBlank()) {
            throw new IllegalArgumentException("The password cannot be empty");
        }

        User userRepo = repository.getUser(user.getLogin());
        if (userRepo != null && Objects.equals(user.getOldPassword(), userRepo.getOldPassword())) {
            userRepo.setOldPassword(user.getNewPassword());
            return repository.addUser(userRepo);
        } else {
            throw new ConflictException("Invalid user password");
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
