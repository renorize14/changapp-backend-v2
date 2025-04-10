package cl.changapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import cl.changapp.entity.notrelated.Message;
import cl.changapp.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long receiverId,
            @RequestParam String content
    ) {
        Long senderId = Long.valueOf(userDetails.getUsername()); 
        return ResponseEntity.ok(chatService.sendMessage(senderId, receiverId, content));
    }

    @GetMapping("/conversation/{otherUserId}")
    public ResponseEntity<List<Message>> getConversation(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long otherUserId
    ) {
        Long userId = Long.valueOf(userDetails.getUsername());
        return ResponseEntity.ok(chatService.getConversation(userId, otherUserId));
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Message>> getUnreadMessages(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = Long.valueOf(userDetails.getUsername());
        return ResponseEntity.ok(chatService.getUnreadMessages(userId));
    }
}
