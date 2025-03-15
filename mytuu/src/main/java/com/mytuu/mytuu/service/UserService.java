package com.mytuu.mytuu.service;

import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists!");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new RuntimeException("Passwords do not match!");
        }

        return userRepository.save(user);
    }

    //Update information for users
    public User updateUserInfo(Long userId, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(userId);
    
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
    
        User existingUser = userOptional.get();
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
        existingUser.setGender(updatedUser.getGender());
    
        return userRepository.save(existingUser);
    }

    //Auth for User
    public User authenticateUser(String identifier, String password) {
        Optional<User> userOptional = userRepository.findByUsername(identifier);
        
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(identifier);
        }
        
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByPhoneNumber(identifier);
        }
    
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
    
        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }
        
        return user;
    }
    
}
