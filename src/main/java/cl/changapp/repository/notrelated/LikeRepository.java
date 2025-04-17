package cl.changapp.repository.notrelated;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.changapp.entity.notrelated.Like;

import java.util.Optional;

public interface LikeRepository extends MongoRepository<Like, String> {
    Optional<Like> findByPostIdAndUserId(String postId, String userId);
    long countByPostId(String postId);
    void deleteByPostIdAndUserId(String postId, String userId);
}
