package org.example.soundly.controller;

import lombok.RequiredArgsConstructor;
import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.SongReaction;
import org.example.soundly.service.SongReactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongReactionController {
    private final SongReactionService songReactionService;

    @PostMapping("/{songId}/like")
    public String likeSong(@PathVariable long songId) {
        return songReactionService.likeSong(songId);
    }
    @PostMapping("/{songId}/dislike")
    public String dislikeSong(@PathVariable Long songId) {
        return songReactionService.dislikeSong(songId);
    }

    @DeleteMapping("/{songId}/reaction")
    public String removeReaction(@PathVariable Long songId) {
        return songReactionService.removeReaction(songId);
    }

    @GetMapping("/liked")
    public List<SongResponse> getLikedSongs() {
        return songReactionService.getLikedSongs();
    }
}
