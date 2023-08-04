package com.memora.api.data.dto;

import lombok.Data;
@Data
public class UserDto {
    private long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private long birthDate;
}
