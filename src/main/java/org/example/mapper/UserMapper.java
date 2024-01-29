package org.example.mapper;

import lombok.experimental.UtilityClass;
import org.example.dto.UserInfo;
import org.example.entity.User;

@UtilityClass
public class UserMapper {
    public UserInfo toUserInfo(User user) {
        return new UserInfo(user.getLogin(), user.getFirstName(), user.getLastName());
    }

    public User toUser(UserInfo userInfo) {
        return new User(null, userInfo.getLogin(), userInfo.getFirstName(), userInfo.getLastName());
    }

}
