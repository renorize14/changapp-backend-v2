package cl.changapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.changapp.dto.ChatRequestDTO;
import cl.changapp.entity.related.ChatRequest;
import cl.changapp.entity.related.User;
import cl.changapp.repository.related.ChatRequestRepository;
import cl.changapp.repository.related.UserRepository;

@Service
public class ChatRequestService {

    private ChatRequestRepository chatRequestRepository;

    private UserRepository userRepository;
    
    public ChatRequestService(ChatRequestRepository chatRequestRepository, UserRepository userRepository) {
    	this.chatRequestRepository = chatRequestRepository;
    	this.userRepository = userRepository;
    }

    public ChatRequest createRequest(ChatRequestDTO dto) {
    	
    	List<ChatRequest> verifyRequest = chatRequestRepository.findByRequesterIdAndRequestedId(dto.getSenderId(), dto.getReceiverId());
    	
    	if ( verifyRequest.isEmpty() ) {
    		User sender = userRepository.findById(dto.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender not found"));
            User receiver = userRepository.findById(dto.getReceiverId())
                    .orElseThrow(() -> new RuntimeException("Receiver not found"));

            ChatRequest request = new ChatRequest();
            request.setRequester(sender);
            request.setRequested(receiver);
            request.setRequestedMessage(dto.getMessage());
            request.setCreatedAt(LocalDateTime.now());
            request.setStatus(false);

            return chatRequestRepository.save(request);
    	}
    	else {
    		return null;
    	}
        
    }

    public List<ChatRequest> getSentRequests(Long senderId) {
    	try {
    		return chatRequestRepository.findByRequesterId(senderId);
		} catch (Exception e) {
			return new ArrayList<>();
		}
        
    }

    public List<ChatRequest> getPendingRequestsForReceiver(Long receiverId) {
    	try {
    		return chatRequestRepository.findByRequestedIdAndStatus(receiverId, false);
		} catch (Exception e) {
			return new ArrayList<>();
		}
        
    }
}
