package com.example.boopromovieapp.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String email;
    private String password;
    private String repeatPassword;
    private Integer roleId;

}