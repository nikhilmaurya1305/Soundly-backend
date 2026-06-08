package org.example.soundly.serviceIMP;

import lombok.RequiredArgsConstructor;
import org.example.soundly.entity.Song;
import org.example.soundly.repository.SongRepository;
import org.example.soundly.service.SongService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
}
