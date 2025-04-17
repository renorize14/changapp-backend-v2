package cl.changapp.repository.related;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.changapp.entity.related.ChatRequest;

import java.util.List;

public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long> {
    List<ChatRequest> findByRequesterId(Long requesterId);
    List<ChatRequest> findByRequestedIdAndStatus(Long receiverId, Boolean status);
    List<ChatRequest> findByRequesterIdAndRequestedId(Long requesterId, Long requestedId);
    
}
