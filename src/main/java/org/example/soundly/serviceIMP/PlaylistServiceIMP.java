package org.example.soundly.serviceIMP;

import lombok.RequiredArgsConstructor;
import org.example.soundly.Enum.Role;
import org.example.soundly.dto.PlaylistDetailsResponse;
import org.example.soundly.dto.PlaylistResponse;
import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.Playlist;
import org.example.soundly.entity.Song;
import org.example.soundly.entity.User;
import org.example.soundly.repository.PlaylistRepository;
import org.example.soundly.repository.SongRepository;
import org.example.soundly.repository.UserRepository;
import org.example.soundly.service.PlaylistService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistServiceIMP implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Override
    public String createPlaylist(String name) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        Playlist playlist = Playlist.builder()
                .name(name)
                .creator(user)
                .build();

        playlistRepository.save(playlist);

        return "Playlist created successfully";
    }

    @Override
    public String addSongToPlaylist(
            Long playlistId,
            Long songId) {

        Playlist playlist =
                playlistRepository.findById(playlistId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Playlist not found"));

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        if (currentUser.getRole() != Role.ADMIN
                &&
                playlist.getCreator().getId() != currentUser.getId()) {

            throw new RuntimeException(
                    "You can only modify your own playlist");
        }

        Song song =
                songRepository.findById(songId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Song not found"));

        playlist.getSongs().add(song);

        playlistRepository.save(playlist);

        return "Song added to playlist";
    }

    @Override
    public List<SongResponse> getPlaylistSongs(
            Long playlistId) {

        Playlist playlist =
                playlistRepository.findById(playlistId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Playlist not found"));

        return playlist.getSongs()
                .stream()
                .map(song -> SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .artist(song.getArtist())
                        .genre(song.getGenre())
                        .build())
                .toList();
    }

    @Override
    public String removeSongFromPlaylist(
            Long playlistId,
            Long songId) {

        Playlist playlist =
                playlistRepository.findById(playlistId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Playlist not found"));

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        if (currentUser.getRole() != Role.ADMIN
                &&
                playlist.getCreator().getId() != currentUser.getId()) {

            throw new RuntimeException(
                    "You can only modify your own playlist");
        }

        Song song =
                songRepository.findById(songId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Song not found"));

        playlist.getSongs().remove(song);

        playlistRepository.save(playlist);

        return "Song removed from playlist";
    }

    @Override
    public List<PlaylistResponse> getAllPlaylists() {

        return playlistRepository
                .findAll()
                .stream()
                .map(playlist ->
                        PlaylistResponse.builder()
                                .id(playlist.getId())
                                .name(playlist.getName())
                                .build())
                .toList();
    }

    @Override
    public PlaylistDetailsResponse getPlaylistById(
            Long playlistId) {

        Playlist playlist =
                playlistRepository.findById(playlistId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Playlist not found"));

        List<SongResponse> songs =
                playlist.getSongs()
                        .stream()
                        .map(song ->
                                SongResponse.builder()
                                        .id(song.getId())
                                        .title(song.getTitle())
                                        .artist(song.getArtist())
                                        .genre(song.getGenre())
                                        .build())
                        .toList();

        return PlaylistDetailsResponse.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .songs(songs)
                .build();
    }

    @Override
    public String deletePlaylist(
            Long playlistId) {

        Playlist playlist =
                playlistRepository.findById(playlistId)
                        .orElseThrow();

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        if (currentUser.getRole() != Role.ADMIN
                &&
                playlist.getCreator().getId() != currentUser.getId()) {

            throw new RuntimeException(
                    "You can only delete your own playlist");
        }

        playlistRepository.delete(playlist);

        return "Playlist deleted successfully";
    }
}