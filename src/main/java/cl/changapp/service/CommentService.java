package cl.changapp.service;

import org.springframework.stereotype.Service;

import cl.changapp.entity.notrelated.Comment;
import cl.changapp.repository.notrelated.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepo;

    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment save(Comment comment) {
        return commentRepo.save(comment);
    }

    public List<Comment> getByPostId(String postId) {
        return commentRepo.findByPostIdOrderByTimestampDesc(postId);
    }
    
    public boolean deleteComment(String commentId) {
    	commentRepo.deleteById(commentId);
        return true;
    }
}