package org.example.user;

import org.example.dto.CreateUserDto;
import org.example.dto.ResultUserDto;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private CreateUserDto createUserDto;
    private User expectedUser;

    public UserServiceImplTest() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @BeforeEach
    public void setUp() {
        createUserDto = new CreateUserDto("user", "firstName",
                "lastName", "password");
        expectedUser = new User(1L, "user", "firstName", "lastName", "password");
    }

    @Test// Добавляем юзера = проверяем сохранился ли.
    public void addNewUser_valid() {
        when(userRepository.findByLogin(createUserDto.getLogin())).thenReturn(null);
        User user = UserMapper.createUserDtoToUser(createUserDto);
        when(userRepository.addAndUpdate(user)).thenReturn(expectedUser);

        ResultUserDto actualUserDto = userService.create(createUserDto);

        verify(userRepository).addAndUpdate(expectedUser);
        assertEquals(actualUserDto.getLogin(), expectedUser.getLogin());
    }

    @Test //Проверяем работу валидации по не корректному логину.
    public void addNewUser_notValidLogin() {
        createUserDto.setLogin(" ");

        assertThrows(IllegalArgumentException.class,
                () -> userService.create(createUserDto));
    }

    @Test//Проверяем работу валидации по не корректному паролю.
    public void addNewUser_notValidPassword() {
        createUserDto.setPassword("");

        assertThrows(IllegalArgumentException.class,
                () -> userService.create(createUserDto));
    }
}
