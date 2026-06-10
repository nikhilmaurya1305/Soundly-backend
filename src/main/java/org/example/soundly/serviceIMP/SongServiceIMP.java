package org.example.soundly.serviceIMP;

import lombok.RequiredArgsConstructor;
import org.example.soundly.Enum.ReactionType;
import org.example.soundly.Enum.Role;
import org.example.soundly.dto.SongResponse;
import org.example.soundly.entity.Song;
import org.example.soundly.repository.SongReactionRepository;
import org.example.soundly.repository.SongRepository;
import org.example.soundly.service.SongService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.example.soundly.entity.User;
import org.example.soundly.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongServiceIMP implements SongService {

    private final SongRepository songRepository;

    private final UserRepository userRepository;

    private final SongReactionRepository songReactionRepository;

    @Override
    public String uploadSong(
            String title,
            String artist,
            String genre,
            MultipartFile file) {

        try {

            String uploadDir =
                    System.getProperty("user.dir")
                            + File.separator
                            + "uploads"
                            + File.separator
                            + "songs"
                            + File.separator;

            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            String filePath = uploadDir + fileName;

            file.transferTo(new File(filePath));

            // Get currently logged-in user
            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            String email = authentication.getName();

            User user = userRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));

            Song song = Song.builder()
                    .title(title)
                    .artist(artist)
                    .genre(genre)
                    .filePath(filePath)
                    .uploader(user)
                    .build();

            songRepository.save(song);

            return "Song uploaded successfully";

        } catch (IOException e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to upload song",e
            );
        }
    }

    @Override
    public List<SongResponse> getAllSongs() {

        return songRepository.findAll()
                .stream()
                .map(song -> {

                    long likes =
                            songReactionRepository
                                    .countBySongIdAndReactionType(
                                            song.getId(),
                                            ReactionType.LIKE);

                    long dislikes =
                            songReactionRepository
                                    .countBySongIdAndReactionType(
                                            song.getId(),
                                            ReactionType.DISLIKE);

                    return SongResponse.builder()
                            .id(song.getId())
                            .title(song.getTitle())
                            .artist(song.getArtist())
                            .genre(song.getGenre())
                            .likes(likes)
                            .dislikes(dislikes)
                            .build();
                })
                .toList();
    }

    @Override
    public Song getSongById(long id){
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found"));
    }

    @Override
    public String deleteSong(Long songId){

        Song song = songRepository
                .findById(songId)
                .orElseThrow();

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow();

        System.out.println("Current User ID: " + currentUser.getId());
        System.out.println("Current User Role: " + currentUser.getRole());

        System.out.println("Song Uploader: " + song.getUploader());

        if(song.getUploader() != null){
            System.out.println("Uploader ID: " + song.getUploader().getId());
        }

        if(currentUser.getRole() != Role.ADMIN
                &&
                song.getUploader().getId() != currentUser.getId()) {

            throw new RuntimeException(
                    "You can only delete your own songs");
        }

        songRepository.delete(song);

        return "Song deleted successfully";
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

    @Override
    public List<SongResponse> searchSongsByGenre(String genre){
        return songRepository
                .findByGenreContainingIgnoreCase(genre)
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
