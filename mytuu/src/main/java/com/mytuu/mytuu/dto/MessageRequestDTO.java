package com.mytuu.mytuu.dto;

import lombok.Data;

@Data
public class MessageRequestDTO {
    private Long senderId;
    private Long receiverId;
    private String content;
    private Long conversationId;
}