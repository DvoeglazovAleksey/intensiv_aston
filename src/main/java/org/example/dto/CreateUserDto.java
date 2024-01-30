package org.example.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserDto {
    private String login;
    private String firstName;
    private String lastName;
    private String password;
}
