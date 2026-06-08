package org.example.soundly.dto;

import lombok.Data;

@Data
public class SongUploadRequest {

    private String title;
    private String artist;
    private String genre;
}
