package com.mytuu.mytuu.dto;

import lombok.Data;
import java.util.List;

@Data
public class ConversationRequestDTO {
    private List<Long> participantIds;
}
