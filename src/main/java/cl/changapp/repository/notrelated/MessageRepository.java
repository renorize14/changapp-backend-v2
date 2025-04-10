package cl.changapp.repository.notrelated;


import org.springframework.data.mongodb.repository.MongoRepository;

import cl.changapp.entity.notrelated.Message;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
        Long senderId, Long receiverId, Long receiverId2, Long senderId2
    );

    List<Message> findByReceiverIdAndReadFalse(Long receiverId);
}
