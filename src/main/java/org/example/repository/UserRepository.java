package org.example.repository;

import org.example.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User findById(long id);

    Optional<User> findByLogin(String login);

    List<User> getAll();

    User addAndUpdate(User user);
}
