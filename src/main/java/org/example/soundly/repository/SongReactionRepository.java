package org.example.soundly.repository;

import org.example.soundly.entity.Song;
import org.example.soundly.entity.SongReaction;
import org.example.soundly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongReactionRepository extends JpaRepository<SongReaction, Long> {
    Optional<SongReaction> findByUserAndSong(
            User user,
            Song song);

    long countBySongIdAndReactionType(
            Long songId,
            org.example.soundly.Enum.ReactionType reactionType);

    List<SongReaction> findByUserIdAndReactionType(
            Long userId,
            org.example.soundly.Enum.ReactionType reactionType);
}
