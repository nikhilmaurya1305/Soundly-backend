package org.example.soundly.controller;

import lombok.RequiredArgsConstructor;
import org.example.soundly.entity.Song;
import org.example.soundly.service.SongService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @PostMapping("/upload")
    public String save(@RequestParam String title, @RequestParam String artist, @RequestParam String genre, @RequestParam MultipartFile file) {
        System.out.println("Upload API HIT");
        return songService.uploadSong(title, artist, genre, file);
    }


}
