package cl.changapp.repository.related;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.changapp.entity.related.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatIdOrderBySentAtAsc(Long chatId);
}