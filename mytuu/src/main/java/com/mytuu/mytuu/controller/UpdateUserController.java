package com.mytuu.mytuu.controller;

import com.mytuu.mytuu.dto.UserUpdateDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.service.UpdateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// UpdateInfo API
@RestController
@RequestMapping("/api/users")
public class UpdateUserController {
    @Autowired
    private UpdateUserService updateUserService;

    //API for update user info
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO info) {
        try {
            User user = updateUserService.updateUserInfo(id, info);
            return ResponseEntity.ok(user);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}