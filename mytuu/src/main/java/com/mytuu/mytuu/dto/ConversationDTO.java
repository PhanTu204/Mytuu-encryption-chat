package com.mytuu.mytuu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
    private Long id;
    private Long partnerId;
    private String partnerName;
    private String partnerAvatar;
    private LocalDateTime lastActivityTime;
    private MessageDTO lastMessage;
}