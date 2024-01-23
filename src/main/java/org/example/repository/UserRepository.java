package org.example.repository;

import org.example.dto.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    User getUser(String login);

    User addUser(User user);
}
