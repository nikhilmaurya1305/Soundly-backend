package org.example.soundly.service;

import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.SongReaction;

import java.util.List;

public interface SongReactionService {

    String likeSong(Long songId);
    String dislikeSong(Long songId);
    String removeReaction(Long songId);

    List<SongResponse> getLikedSongs();
}
