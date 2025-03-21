package com.mytuu.mytuu.controller;

import com.mytuu.mytuu.dto.UserUpdateDTO;
import com.mytuu.mytuu.dto.LoginRequestDTO;
import com.mytuu.mytuu.dto.RegisterDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //API for register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        try {
            User registeredUser = userService.registerUser(registerDTO);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //API for update user info
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO info) {
        try {
            User user = userService.updateUserInfo(id, info);
            return ResponseEntity.ok(user);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //API for login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            User authenticatedUser = userService.authenticateUser(loginRequestDTO.getIdentifier(), loginRequestDTO.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
