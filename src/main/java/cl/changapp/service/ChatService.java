package cl.changapp.service;

import org.springframework.stereotype.Service;

import cl.changapp.entity.notrelated.Message;
import cl.changapp.repository.notrelated.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final MessageRepository messageRepository;

    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        System.out.println("aaaaa");
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
}