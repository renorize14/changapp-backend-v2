package cl.changapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import cl.changapp.dto.ChatMessageDTO;
import cl.changapp.service.ChatService;

@Controller
public class ChatWebSocketController {
	
	private final ChatService chatService;
	
	public ChatWebSocketController(ChatService chatService) {
		this.chatService = chatService;
	}

    @MessageMapping("/chat/send") // recibe desde /app/chat/send
    @SendTo("/topic/messages") // lo transmite a los subscritos a /topic/messages
    public ChatMessageDTO sendMessage(ChatMessageDTO message) {
    	chatService.sendMessage(Long.parseLong(message.getSender()), Long.parseLong(message.getReceiver()), message.getContent());
        return message;
    }
}