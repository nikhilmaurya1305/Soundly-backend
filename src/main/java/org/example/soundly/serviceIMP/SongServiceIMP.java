package org.example.soundly.serviceIMP;

import lombok.RequiredArgsConstructor;
import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.Song;
import org.example.soundly.repository.SongRepository;
import org.example.soundly.service.SongService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongServiceIMP implements SongService {
    private final SongRepository songRepository;

    @Override
    public String uploadSong(String title, String artist, String genre, MultipartFile file) {
        try{
            String uploadDir =
                    System.getProperty("user.dir")
                            + File.separator
                            + "uploads"
                            + File.separator
                            + "songs"
                            + File.separator;

            File directory = new File(uploadDir);

            if(!directory.exists()){
                directory.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            String filePath = uploadDir + fileName;




            file.transferTo(new File(filePath));



            Song song = Song.builder().title(title).artist(artist).genre(genre).filePath(filePath).build();


            songRepository.save(song);


            return "Song uploaded successfully";

        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Failed to upload song", e);
        }
    }

    @Override
    public List<SongResponse> getAllSongs() {
        return songRepository.findAll()
                .stream()
                .map(song -> SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .artist(song.getArtist())
                        .genre(song.getGenre())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Song getSongById(long id){
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found"));
    }

    @Override
    public void deleteSong(long id){

        Song song = songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found"));

        File file = new File(song.getFilePath());

        if(file.exists()){
            file.delete();
        }
        songRepository.delete(song);
    }

    @Override
    public List<SongResponse> searchSongs(String title){
        return songRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(song -> SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .artist(song.getArtist())
                        .genre(song.getGenre())
                        .build())
                .toList();
    }
}
