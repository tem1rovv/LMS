package uz.pdp.lmsad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lmsad.entity.ChatMessage;


import java.util.List;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
List<ChatMessage> findByCourseIdOrderBySentAtAsc(Long courseId);
List<ChatMessage> findBySenderOrRecipientIdOrderBySentAtAsc(String sender, Long recipientId);
}