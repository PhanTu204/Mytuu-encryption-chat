package com.mytuu.mytuu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String username;
    private String password;
    private String confirmPassword;
}
