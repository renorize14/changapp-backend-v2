package cl.changapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import cl.changapp.config.ChatWebSocketHandler;
import cl.changapp.dto.CreateNewChatDTO;
import cl.changapp.dto.SendNewMessageDTO;
import cl.changapp.entity.notrelated.Message;
import cl.changapp.entity.related.Chat;
import cl.changapp.entity.related.ChatMessage;
import cl.changapp.entity.related.User;
import cl.changapp.repository.notrelated.MessageRepository;
import cl.changapp.repository.related.ChatMessageRepository;
import cl.changapp.repository.related.ChatRepository;
import cl.changapp.repository.related.ChatRequestRepository;
import cl.changapp.repository.related.UserRepository;
import cl.changapp.service.ChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;
    private final ChatRequestRepository crRep;
    private final UserRepository uRep;
    private final ChatRepository cRep;
    private final ChatMessageRepository cmRep;
    private final MessageRepository mRep;
    private ChatWebSocketHandler cwsh;

    public ChatController(ChatService chatService, 
    		ChatRequestRepository crRep, 
    		UserRepository uRep,
    		ChatRepository cRep,
    		ChatMessageRepository cmRep,
    		MessageRepository mRep,
    		ChatWebSocketHandler cwsh) {
        this.chatService = chatService;
        this.crRep = crRep;
        this.uRep = uRep;
        this.cRep = cRep;
        this.cmRep = cmRep;
        this.mRep = mRep;
        this.cwsh = cwsh;
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
    
    @GetMapping("/active-chats/{userId}")
    public ResponseEntity<List<Long>> getActiveChats(@PathVariable Long userId) {
        List<Long> activeChats = chatService.getActiveChatsForUser(userId);
        return ResponseEntity.ok(activeChats);
    }
    
    @GetMapping("/history/{userId1}/{userId2}")
    public ResponseEntity<List<Message>> getChatHistory(
            @PathVariable Long userId1,
            @PathVariable Long userId2
    ) {
        List<Message> history = chatService.getChatHistory(userId1, userId2);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/unread-count/{receiverId}")
    public ResponseEntity<Long> countUnreadMessages(@PathVariable Long receiverId) {
        long count = chatService.countUnreadMessages(receiverId);
        return ResponseEntity.ok(count);
    }
    
    @PostMapping("/create")
    public ResponseEntity<List<Message>> acceptAndCreateChat(@RequestBody CreateNewChatDTO createNewChat){
    	
	    try {
	    	//1: Borrar chatRequest
	    	crRep.deleteById(createNewChat.getChatId());
	    	
	    	//2: Insertar chat
	    	
	    	System.out.println("Sender id: " + createNewChat.getSenderId());
	    	System.out.println("Reciever id: " + createNewChat.getReceiverId());
	    	
	    	Optional<User> userOne = uRep.findById(createNewChat.getSenderId());
	    	Optional<User> userTwo = uRep.findById(createNewChat.getReceiverId());   	
	    	
	    	Chat newChat = new Chat();
	    	
	    	newChat.setUserOne(userOne.get());
	    	newChat.setUserTwo(userTwo.get());
	    	
	    	Chat returnedChat = cRep.save(newChat); 
	    	
	    	
	    	//3: Crear chat message
	    	
	    	ChatMessage chatMessage = new ChatMessage();
	    	
	    	chatMessage.setChat(returnedChat);
	    	chatMessage.setMessage(createNewChat.getContent());
	    	chatMessage.setSender(userOne.get());
	    	chatMessage.setSentAt(createNewChat.getTimestamp());
	    	
	    	System.out.println(createNewChat.getContent());
	    	
	    	
	    	ChatMessage returnedChatMessage = cmRep.save(chatMessage);   
	    	
	    	//3: Insertar chat mongoDB asociando el id del chat
	    	
	    	Message newMessage = new Message();
	    	
	    	newMessage.setContent(createNewChat.getContent());
	    	newMessage.setRead(true);
	    	newMessage.setSenderId(createNewChat.getSenderId());
	    	newMessage.setReceiverId(createNewChat.getReceiverId());
	    	newMessage.setTimestamp(createNewChat.getTimestamp());
	    	newMessage.setChatId(chatMessage.getId());
	    	
	    	mRep.save(newMessage);
	    	
	    	List<Message> messages = mRep.findAllByChatId(chatMessage.getId());
	    	
	    	
	    	//4: devolver el chat
	    	
	    	return ResponseEntity.ok(messages);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
    }
    
    
    @GetMapping("/preview-chats/{userId}")
    public List<ChatMessage> getPreviewChats(@PathVariable Long userId) {
        List<Chat> chatsInvolved = cRep.findByUserOneIdOrUserTwoId(userId, userId);
        List<ChatMessage> chatMessageList = new ArrayList<>();

        for (Chat chat : chatsInvolved) {
            ChatMessage chatMessage = cmRep.findByChatId(chat.getId());
            if (chatMessage != null) {
                chatMessageList.add(chatMessage);
            }
        }

        // Ordenar por sentAt de forma descendente
        chatMessageList.sort(Comparator.comparing(ChatMessage::getSentAt).reversed());

        return chatMessageList;
    }
    
    @GetMapping("/chats/{senderId}/{recieverId}")
    public List<Message> getAllMessages(@PathVariable Long senderId, @PathVariable Long recieverId){
    	List<Message> messages = mRep.findConversationBetweenUsers(senderId, recieverId);

    	return messages;
    }
    
    @PostMapping("/send-message")
    public List<Message> sendNewMessage(@RequestBody SendNewMessageDTO newMessage ) {
    	
    	//actualizamos el último mensaje
    	User sender = uRep.findById(newMessage.getSenderId()).get();

    	ChatMessage chatToUpdate = cmRep.findByChatId(newMessage.getChatId());
    	
    	chatToUpdate.setMessage(newMessage.getContent());
    	chatToUpdate.setSender(sender);
    	chatToUpdate.setSentAt(newMessage.getTimestamp());
    	
    	cmRep.save(chatToUpdate);
    	
    	Message mess = new Message();
    	
    	mess.setContent(newMessage.getContent());
    	mess.setRead(true);
    	mess.setSenderId(newMessage.getSenderId());
    	mess.setReceiverId(newMessage.getReceiverId());
    	mess.setTimestamp(newMessage.getTimestamp());
    	mess.setChatId(newMessage.getChatId());
    	
    	mRep.save(mess);
    	
    	List<Message> messages = mRep.findAllByChatId(newMessage.getChatId());
    	
    	try {
    		cwsh.sendMessage(newMessage);
		} catch (Exception e) {
			System.out.println(e);
		}
        
        return messages;
    }
    
    
    
}
