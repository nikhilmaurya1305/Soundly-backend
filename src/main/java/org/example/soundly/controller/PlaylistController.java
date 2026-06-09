package org.example.soundly.controller;

import lombok.RequiredArgsConstructor;
import org.example.soundly.dto.CreatePlaylistRequest;
import org.example.soundly.dto.PlaylistDetailsResponse;
import org.example.soundly.dto.PlaylistResponse;
import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.Playlist;
import org.example.soundly.service.PlaylistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;


    @PostMapping("/create")
    public String createPlaylist(@RequestBody CreatePlaylistRequest request) {
        return playlistService.createPlaylist(request.getName());
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public String addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        return playlistService
                .addSongToPlaylist(
                        playlistId,
                        songId);
    }

    @GetMapping("/{PlaylistId}/songs")
    public List<SongResponse> getPlaylistSongs(@PathVariable Long PlaylistId) {
        return playlistService.getPlaylistSongs(PlaylistId);
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public String removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        return playlistService
                .removeSongFromPlaylist(playlistId, songId);
    }

    @GetMapping
    public List<PlaylistResponse> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{playlistId}")
    public PlaylistDetailsResponse getPlaylistById(@PathVariable Long playlistId) {
        return playlistService.getPlaylistById(playlistId);
    }

    @DeleteMapping("/{playlistId}")
    public String deletePlaylist(@PathVariable Long playlistId) {
        return playlistService.deletePlaylist(playlistId);
    }
}
