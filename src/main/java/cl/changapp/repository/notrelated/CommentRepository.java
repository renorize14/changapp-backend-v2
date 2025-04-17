package cl.changapp.repository.notrelated;


import org.springframework.data.mongodb.repository.MongoRepository;

import cl.changapp.entity.notrelated.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostIdOrderByTimestampDesc(String postId);

}