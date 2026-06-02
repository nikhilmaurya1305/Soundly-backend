package org.example.soundly.Repository;

import org.example.soundly.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositry extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
