package org.example.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserPasswordDto {
    private String login;
    private String oldPassword;
    private String newPassword;
}
