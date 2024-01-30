package org.example.mapper;

import lombok.experimental.UtilityClass;
import org.example.dto.CreateUserDto;
import org.example.dto.ResultUserDto;
import org.example.entity.User;

@UtilityClass
public class UserMapper {
    public ResultUserDto toResultUserDto(User user) {
        return new ResultUserDto(user.getLogin(), user.getFirstName(), user.getLastName());
    }

    public User createUserDtoToUser(CreateUserDto createUserDto) {
        return new User(null, createUserDto.getLogin(), createUserDto.getFirstName(),
                createUserDto.getLastName(), createUserDto.getPassword());
    }
}
