package cl.changapp.controller;

public class ChatWebSocketController {
	
	/*private final ChatService chatService;
	private final SimpMessagingTemplate messagingTemplate;
	
	public ChatWebSocketController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat/send") 
    @SendTo("/topic/messages") 
    public ChatMessageDTO sendMessage(ChatMessageDTO message) {
    	chatService.sendMessage(Long.parseLong(message.getSender()), Long.parseLong(message.getReceiver()), message.getContent());
    	
        messagingTemplate.convertAndSend("/topic/chat/" + message.getReceiver(), message);
        
        NotificationDTO notification = new NotificationDTO("Nuevo mensaje de " + message.getSender());
        messagingTemplate.convertAndSend("/topic/notifications/" + message.getReceiver(), notification);

        
        return message;
    }*/
}