package org.example.repository;

import org.example.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    // Логин за ключ = уникальное значение. Но обычно id выполняет эту функцию.
    private Map<String, User> users = new HashMap<>();

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(String login) {
        return users.get(login);
    }

    @Override
    public User addUser(User user) {
        users.put(user.getLogin(), user);
        return users.get(user.getLogin());
    }
}
