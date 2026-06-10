package org.example.soundly.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongResponse {

    private long id;
    private String title;
    private String artist;
    private String genre;
    private long likes;
    private long dislikes;
}
