package com.mytuu.mytuu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String identifier;
    private String password;
}
