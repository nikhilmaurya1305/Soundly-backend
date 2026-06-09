package org.example.soundly.repository;

import org.example.soundly.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByTitleContainingIgnoreCase(String title);
    List<Song> findByGenreContainingIgnoreCase(String genre);
}
