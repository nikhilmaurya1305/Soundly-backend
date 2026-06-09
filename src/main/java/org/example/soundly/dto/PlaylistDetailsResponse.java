package org.example.soundly.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistDetailsResponse {

    private Long id;
    private String name;
    private List<SongResponse> songs;
}
