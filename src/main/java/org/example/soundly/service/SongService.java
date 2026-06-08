package org.example.soundly.service;

import org.springframework.web.multipart.MultipartFile;

public interface SongService {

    String uploadSong(String title, String artist, String genre, MultipartFile file);
}
