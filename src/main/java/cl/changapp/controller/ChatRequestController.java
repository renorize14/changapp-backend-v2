package cl.changapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import cl.changapp.dto.ChatRequestDTO;
import cl.changapp.entity.notrelated.Message;
import cl.changapp.entity.related.Chat;
import cl.changapp.entity.related.ChatRequest;
import cl.changapp.repository.notrelated.MessageRepository;
import cl.changapp.repository.related.ChatRepository;
import cl.changapp.service.ChatRequestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/chat-requests")
@SecurityRequirement(name = "bearerAuth")
public class ChatRequestController {

    private ChatRequestService chatRequestService;
    private MessageRepository messRep;
    
    public ChatRequestController(ChatRequestService chatRequestService, MessageRepository messRep) {
    	this.chatRequestService = chatRequestService;
    	this.messRep = messRep;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChatRequest(@RequestBody ChatRequestDTO dto) {
        ChatRequest request = chatRequestService.createRequest(dto);
        if ( request == null ) {
        	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("");
        }
        else {
        	Message newMessage = new Message();
        	
        	newMessage.setContent(dto.getMessage());
        	newMessage.setRead(false);
        	newMessage.setReceiverId(dto.getReceiverId());
        	newMessage.setSenderId(dto.getSenderId());
        	newMessage.setTimestamp(LocalDateTime.now());
        	
        	messRep.save(newMessage);
        	
        	return ResponseEntity.status(HttpStatus.CREATED).body(request);
        }
        
        
    }

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<ChatRequest>> getSentRequests(@PathVariable Long senderId) {
        return ResponseEntity.ok(chatRequestService.getSentRequests(senderId));
    }

    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<ChatRequest>> getPendingRequests(@PathVariable Long receiverId) {
        return ResponseEntity.ok(chatRequestService.getPendingRequestsForReceiver(receiverId));
    }
    
    @DeleteMapping("/{reqId}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long reqId) {
        return ResponseEntity.ok(chatRequestService.deleteRequest(reqId));
    }
    
    
    
}