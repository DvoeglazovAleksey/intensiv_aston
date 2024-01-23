package org.example.user;

import org.example.dto.User;
import org.example.repository.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserServiceImplTest {
    // Не как не получается UserService внедрить через конструктор, только так(но все работает).
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    User user = new User("user", "password", "newPassword");

    @Test// Добавляем юзера = проверяем сохранился ли.
    public void addNewUser_valid() {
        User userRepo = userService.addNewUser(user);

        assertEquals(userRepo, user, "User не сохранился");
    }

    @Test //Проверяем работу валидации по не корректному логину.
    public void addNewUser_notValidLogin() {
        user.setLogin(" ");

        assertThrows(IllegalArgumentException.class,
                () -> userService.addNewUser(user));
    }

    @Test//Проверяем работу валидации по не корректному паролю.
    public void addNewUser_notValidPassword() {
        user.setOldPassword("");

        assertThrows(IllegalArgumentException.class,
                () -> userService.addNewUser(user));
    }
}
