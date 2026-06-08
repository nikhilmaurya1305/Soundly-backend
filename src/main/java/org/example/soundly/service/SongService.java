package org.example.soundly.service;

import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService {

    String uploadSong(String title, String artist, String genre, MultipartFile file);

    List<SongResponse> getAllSongs();

    Song getSongById(long id);

    void deleteSong(long id);

    List<SongResponse> searchSongs(String title);
}
