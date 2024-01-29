package org.example.repository;

import org.example.entity.User;

import java.util.List;

public interface UserRepository {
    User findById(long id);

    User findByLogin(String login);

    List<User> getAll();

    User addUser(User user);
}
