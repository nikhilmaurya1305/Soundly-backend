package org.example.soundly.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String artist;
    private String genre;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploader;
}
