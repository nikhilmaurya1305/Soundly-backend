package org.example.soundly.service;

import org.example.soundly.dto.PlaylistDetailsResponse;
import org.example.soundly.dto.PlaylistResponse;
import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.Playlist;

import java.util.List;

public interface PlaylistService {
     String createPlaylist(String name);

    String addSongToPlaylist(Long playlistId, Long songId);

    List<SongResponse> getPlaylistSongs(Long playlistId);

    String removeSongFromPlaylist(Long playlistId, Long songId);

    List<PlaylistResponse> getAllPlaylists();

    PlaylistDetailsResponse getPlaylistById(Long playlistId);

    String deletePlaylist(Long playlistId);}
