package com.mytuu.mytuu.controller;

import com.mytuu.mytuu.dto.LoginRequestDTO;
import com.mytuu.mytuu.service.LoginService;

import java.util.HashMap;
import java.util.Map;

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
            String[] token = loginService.authenticateUser(loginRequestDTO.getIdentifier(), loginRequestDTO.getPassword());

            //trả về token
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", token[0]);
            response.put("refresh", token[1]);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
