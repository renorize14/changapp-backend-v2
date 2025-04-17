package cl.changapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.changapp.service.LikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/likes")
@SecurityRequirement(name = "bearerAuth")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable String postId, @RequestParam String userId) {
        boolean liked = likeService.likePost(postId, userId);
        return liked ? "Liked" : "Already liked";
    }

    @PostMapping("/{postId}/unlike")
    public String unlikePost(@PathVariable String postId, @RequestParam String userId) {
        boolean unliked = likeService.unlikePost(postId, userId);
        return unliked ? "Unliked" : "No like to remove";
    }

    @GetMapping("/{postId}/count")
    public long getLikesCount(@PathVariable String postId) {
        return likeService.getLikesCount(postId);
    }

    @GetMapping("/{postId}/liked-by-user")
    public boolean hasUserLiked(
        @PathVariable String postId,
        @RequestParam String userId
    ) {
        return likeService.hasUserLiked(postId, userId);
    }
}
