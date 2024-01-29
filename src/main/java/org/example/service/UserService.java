package org.example.service;

import org.example.dto.UserInfo;

import java.util.List;

public interface UserService {
    List<UserInfo> getAllUsers();

    UserInfo getAuthentication(long userId);

    UserInfo addNewUser(UserInfo userInfo);

    UserInfo updateUserName(long id, UserInfo userInfo);
}
