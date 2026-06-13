package org.example.soundly.controller;

import lombok.RequiredArgsConstructor;
import org.example.soundly.dto.CommentRequest;
import org.example.soundly.dto.CommentResponse;
import org.example.soundly.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/songs/{songId}/comments")
    public String addComment(@PathVariable long songId, @RequestBody CommentRequest request) {
        return commentService.addComment(songId, request.getContent());
    }

    @GetMapping("/songs/{songId}/comments")
    public List<CommentResponse> getComments(
            @PathVariable Long songId) {

        return commentService.getComments(songId);
    }

    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(
            @PathVariable Long commentId) {

        return commentService.deleteComment(commentId);
    }
}
