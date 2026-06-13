package org.example.soundly.service;

import org.example.soundly.dto.CommentResponse;

import java.util.List;

public interface CommentService {
    String addComment(Long songId,String Content);

    List<CommentResponse> getComments(Long songId);

    String deleteComment(Long commentId);
}
