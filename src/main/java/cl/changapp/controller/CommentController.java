package cl.changapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.changapp.entity.notrelated.Comment;
import cl.changapp.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/{postOwnerId}")
    public ResponseEntity<Comment> addComment(
            @PathVariable String postId,
            @PathVariable String postOwnerId,
            @RequestBody Comment comment
    ) {
        comment.setPostId(postId);
        Comment saved = commentService.save(comment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable String postId) {
        List<Comment> comments = commentService.getByPostId(postId);
        return ResponseEntity.ok(comments);
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable String commentId) {

        boolean deleted = commentService.deleteComment(commentId);

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para borrar este comentario.");
        }
    }
}