package org.example.soundly.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.soundly.Enum.ReactionType;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
