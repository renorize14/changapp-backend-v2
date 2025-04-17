package cl.changapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.changapp.entity.notrelated.Like;
import cl.changapp.repository.notrelated.LikeRepository;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public boolean likePost(String postId, String userId) {
        if (likeRepository.findByPostIdAndUserId(postId, userId).isPresent()) {
            return false; // Ya le dio like
        }

        Like like = new Like();
        like.setPostId(postId);
        like.setUserId(userId);
        likeRepository.save(like);
        return true;
    }

    public boolean unlikePost(String postId, String userId) {
        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return true;
        }
        return false;
    }

    public long getLikesCount(String postId) {
        return likeRepository.countByPostId(postId);
    }

    public boolean hasUserLiked(String postId, String userId) {
        return likeRepository.findByPostIdAndUserId(postId, userId).isPresent();
    }
}
