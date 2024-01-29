package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserInfo;
import org.example.entity.User;
import org.example.exception.exceptions.ConflictException;
import org.example.exception.exceptions.NotFoundException;
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
    public List<UserInfo> getAllUsers() {
        List<User> users = repository.getAll();

        if (users.isEmpty()) {
            return new ArrayList<>();
        } else {
            return users.stream()
                    .map(UserMapper::toUserInfo)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public UserInfo getAuthentication(long id) {
        User userRepo = repository.findById(id);
        if (Objects.isNull(userRepo)) {
            throw new NotFoundException("User with id = " + id + " does not exist");
        }
        return UserMapper.toUserInfo(userRepo);
    }

    @Override
    @Transactional
    public UserInfo addNewUser(UserInfo userInfo) {
        if (Objects.isNull(userInfo)) {
            throw new IllegalArgumentException("There is no data about the user");
        }
        if (userInfo.getLogin().isBlank()) {
            throw new IllegalArgumentException("Enter login");
        }
        if (userInfo.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Enter firstName");
        }
        if (userInfo.getLastName().isBlank()) {
            throw new IllegalArgumentException("Enter lastName");
        }
        // Проверяю нет ли в базе пользователя с таким логином который хотим создать.
        User checkUser = repository.findByLogin(userInfo.getLogin());
        if (Objects.isNull(checkUser)) {
            User userRepo = repository.addUser(UserMapper.toUser(userInfo));
            return UserMapper.toUserInfo(userRepo);
        } else {
            throw new ConflictException("User with login =  " + userInfo.getLogin() +
                    " already exists, please change your login");
        }
    }

    @Override
    public UserInfo updateUserName(long id, UserInfo userInfo) {
//      Проверяем наличие пользователя в бд.
        User userRepo = repository.findById(id);
        if (Objects.isNull(userRepo)) {
            throw new NotFoundException("Not user in BD");
        }
        //        Если пришло имя = меняем  имя.
        if (!userInfo.getFirstName().isBlank()) {
            userRepo.setFirstName(userInfo.getFirstName());
        }
//        Если пришла фамилия = меняем фамилию.
        if (!userInfo.getLastName().isBlank()) {
            userRepo.setLastName(userInfo.getLastName());
        }
        return UserMapper.toUserInfo(repository.addUser(userRepo));
    }
}
