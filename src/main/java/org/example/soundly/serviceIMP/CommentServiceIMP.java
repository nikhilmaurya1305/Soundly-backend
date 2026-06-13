package org.example.soundly.serviceIMP;

import lombok.RequiredArgsConstructor;
import org.example.soundly.Enum.Role;
import org.example.soundly.dto.CommentResponse;
import org.example.soundly.entity.Comment;
import org.example.soundly.entity.Song;
import org.example.soundly.entity.User;
import org.example.soundly.repository.CommentRepository;
import org.example.soundly.repository.SongRepository;
import org.example.soundly.repository.UserRepository;
import org.example.soundly.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceIMP
        implements CommentService {

    private final CommentRepository commentRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow();
    }

    @Override
    public String addComment(
            Long songId,
            String content) {

        User user = getCurrentUser();

        Song song =
                songRepository.findById(songId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Song not found"));

        Comment comment =
                Comment.builder()
                        .content(content)
                        .song(song)
                        .user(user)
                        .build();

        commentRepository.save(comment);

        return "Comment added successfully";
    }

    @Override
    public List<CommentResponse> getComments(
            Long songId) {

        return commentRepository
                .findBySongId(songId)
                .stream()
                .map(comment ->
                        CommentResponse.builder()
                                .id(comment.getId())
                                .content(comment.getContent())
                                .username(
                                        comment.getUser()
                                                .getUsername())
                                .build())
                .toList();
    }

    @Override
    public String deleteComment(
            Long commentId) {

        Comment comment =
                commentRepository.findById(commentId)
                        .orElseThrow();

        User currentUser =
                getCurrentUser();

        if(currentUser.getRole() != Role.ADMIN
                &&
                comment.getUser().getId()
                        != currentUser.getId()) {

            throw new RuntimeException(
                    "You can only delete your own comments");
        }

        commentRepository.delete(comment);

        return "Comment deleted successfully";
    }
}