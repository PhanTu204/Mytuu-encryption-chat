package com.mytuu.mytuu.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 50)
    private String username;
    
    @Column(nullable = false)
    @Size(min = 6)
    private String password;

    @Transient
    @Size(min = 6)
    private String confirmPassword;
    
    public String getConfirmPassword() { 
        return confirmPassword; 
        }

    public void setConfirmPassword(String confirmPassword) { 
        this.confirmPassword = confirmPassword; 
    }

    private String fullName;
    
    @Email
    private String email;
    
    private String phoneNumber;
    
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    
    private String gender;
    
}
