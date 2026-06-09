package org.example.soundly.controller;

import lombok.RequiredArgsConstructor;
import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.Song;
import org.example.soundly.service.SongService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @PostMapping("/upload")
    public String save(@RequestParam String title, @RequestParam String artist, @RequestParam String genre, @RequestParam MultipartFile file) {
        return songService.uploadSong(title, artist, genre, file);
    }

    @GetMapping
    public List<SongResponse> getSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/play/{id}")
    public ResponseEntity<Resource> playSong(@PathVariable long id) throws MalformedURLException {
        Song song = songService.getSongById(id);

        Path path = Paths.get(song.getFilePath());

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "audio/mpeg").body(resource);
    }

    @GetMapping("/search")
    public List<SongResponse> getSongsByTitle(@RequestParam String title) {
        return songService.searchSongs(title);
    }

    @GetMapping("/byGenre")
    public List<SongResponse> getSongsByGenre(@RequestParam String genre) {
        return songService.searchSongsByGenre(genre);
    }

    @DeleteMapping("/{id}")
    public String deleteSong(@PathVariable long id) {
        songService.deleteSong(id);

        return "Song deleted Successfully";
    }
}
