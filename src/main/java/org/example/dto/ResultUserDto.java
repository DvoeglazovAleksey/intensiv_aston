package org.example.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultUserDto {
    private String login;
    private String firstName;
    private String lastName;
}
