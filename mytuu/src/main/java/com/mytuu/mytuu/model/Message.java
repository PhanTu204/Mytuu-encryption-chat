package com.mytuu.mytuu.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;
    
    @ManyToOne
    private User receiver;

    @ManyToOne
    private Conversation conversation;

    private String content;
    private boolean isRead = false;
    private LocalDateTime timestamp;
    

}
