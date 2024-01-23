package org.example.service;

import org.example.dto.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    boolean getAuthentication(User user);

    User addNewUser(User user);

    User updatePasswordUser(User user);
}
