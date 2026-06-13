package org.example.soundly.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongResponse {

    private Long id;
    private String title;
    private String artist;
    private String genre;
    private Long likes;
    private Long dislikes;
}
