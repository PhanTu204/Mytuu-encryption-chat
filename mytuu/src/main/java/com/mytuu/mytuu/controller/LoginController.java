package com.mytuu.mytuu.controller;

import com.mytuu.mytuu.dto.LoginRequestDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class LoginController {
    @Autowired
    private LoginService loginService;


    //API for login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            User authenticatedUser = loginService.authenticateUser(loginRequestDTO.getIdentifier(), loginRequestDTO.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
