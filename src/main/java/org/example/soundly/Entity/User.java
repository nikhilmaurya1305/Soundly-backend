package org.example.soundly.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.soundly.Enum.Role;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String Username;
    @Column(unique=true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
