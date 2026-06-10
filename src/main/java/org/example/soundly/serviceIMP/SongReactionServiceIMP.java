package org.example.soundly.serviceIMP;

import lombok.RequiredArgsConstructor;
import org.example.soundly.Enum.ReactionType;
import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.Song;
import org.example.soundly.entity.SongReaction;
import org.example.soundly.entity.User;
import org.example.soundly.repository.SongReactionRepository;
import org.example.soundly.repository.SongRepository;
import org.example.soundly.repository.UserRepository;
import org.example.soundly.service.SongReactionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongReactionServiceIMP implements SongReactionService {
    private final SongReactionRepository songReactionRepository;
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
    public String likeSong(Long songId){
        User user = getCurrentUser();

        Song song = songRepository
                .findById(songId)
                .orElseThrow(() ->
                        new RuntimeException("Song not found"));

        SongReaction reaction =
                songReactionRepository
                        .findByUserAndSong(user, song)
                        .orElse(
                                SongReaction.builder()
                                        .user(user)
                                        .song(song)
                                        .build()
                        );

        reaction.setReactionType(
                ReactionType.LIKE);

        songReactionRepository.save(reaction);

        return "Song liked successfully";
    }
    @Override
    public String dislikeSong(Long songId){
        User user = getCurrentUser();

        Song song = songRepository
                .findById(songId)
                .orElseThrow(() ->
                        new RuntimeException("Song not found"));

        SongReaction reaction =
                songReactionRepository
                        .findByUserAndSong(user, song)
                        .orElse(
                                SongReaction.builder()
                                        .user(user)
                                        .song(song)
                                        .build()
                        );

        reaction.setReactionType(
                ReactionType.DISLIKE);

        songReactionRepository.save(reaction);

        return "Song disliked successfully";
    }
    @Override
    public String removeReaction(Long songId){
        User user = getCurrentUser();

        Song song = songRepository
                .findById(songId)
                .orElseThrow(() ->
                        new RuntimeException("Song not found"));

        SongReaction reaction =
                songReactionRepository
                        .findByUserAndSong(user, song)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "No reaction found"));

        songReactionRepository.delete(reaction);

        return "Reaction removed successfully";
    }
    @Override
    public List<SongResponse> getLikedSongs() {
        User user = getCurrentUser();

        return songReactionRepository
                .findByUserIdAndReactionType(
                        user.getId(),
                        ReactionType.LIKE)
                .stream()
                .map(reaction ->
                        SongResponse.builder()
                                .id(reaction.getSong().getId())
                                .title(reaction.getSong().getTitle())
                                .artist(reaction.getSong().getArtist())
                                .genre(reaction.getSong().getGenre())
                                .build())
                .toList();

    }
}
