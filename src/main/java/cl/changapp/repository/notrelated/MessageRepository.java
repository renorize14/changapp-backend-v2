package cl.changapp.repository.notrelated;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cl.changapp.entity.notrelated.Message;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
        Long senderId, Long receiverId, Long receiverId2, Long senderId2
    );

    List<Message> findByReceiverIdAndReadFalse(Long receiverId);
    
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
    
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
    	    Long sender1, Long receiver1, Long sender2, Long receiver2
    	);
    
    long countByReceiverIdAndRead(Long receiverId, boolean read);
    
    List<Message> findAllByChatId(Long chatId);
    
    @Query("{ '$or': [ { 'senderId': ?0, 'receiverId': ?1 }, { 'senderId': ?1, 'receiverId': ?0 } ] }")
    List<Message> findConversationBetweenUsers(Long userId1, Long userId2);
    
}
