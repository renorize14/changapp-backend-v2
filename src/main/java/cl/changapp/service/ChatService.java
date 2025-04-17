package cl.changapp.service;

import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import cl.changapp.entity.notrelated.Message;
import cl.changapp.entity.related.ChatRequest;
import cl.changapp.repository.notrelated.MessageRepository;
import cl.changapp.repository.related.ChatMessageRepository;
import cl.changapp.repository.related.ChatRequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final ChatRequestRepository crRep;
    private final ChatMessageRepository cmRep;

    public ChatService(MessageRepository messageRepository, ChatRequestRepository crRep,ChatMessageRepository cmRep) {
        this.messageRepository = messageRepository;
        this.crRep = crRep;
        this.cmRep = cmRep;
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long user1Id, Long user2Id) {
        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
            user1Id, user2Id, user1Id, user2Id
        );
    }

    public List<Message> getUnreadMessages(Long userId) {
        return messageRepository.findByReceiverIdAndReadFalse(userId);
    }

    public void markMessagesAsRead(List<Message> messages) {
        messages.forEach(msg -> msg.setRead(true));
        messageRepository.saveAll(messages);
    }
    
    public List<Long> getActiveChatsForUser(Long userId) {
        List<Message> messages = messageRepository
            .findBySenderIdOrReceiverId(userId, userId);

        return messages.stream()
        	    .flatMap((Message msg) -> Stream.of(msg.getSenderId(), msg.getReceiverId()))
        	    .filter(id -> !id.equals(userId))
        	    .distinct()
        	    .collect(Collectors.toList());
    }
    
    public List<Message> getChatHistory(Long userId1, Long userId2) {
        return messageRepository
            .findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                userId1, userId2, userId1, userId2
            );
    }
    
    public long countUnreadMessages(Long receiverId) {
        return messageRepository.countByReceiverIdAndRead(receiverId, false);
    }
    
    
    public List<ChatRequest> getChatRequestsByRecieverId(Long recieverId){
    	
    	List<ChatRequest> requests = crRep.findByRequestedIdAndStatus(recieverId, true);
    	
    	return requests;
    }
}