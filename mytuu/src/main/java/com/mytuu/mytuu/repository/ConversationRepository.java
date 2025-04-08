package com.mytuu.mytuu.repository;

import com.mytuu.mytuu.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByParticipantsContaining(User user);
}