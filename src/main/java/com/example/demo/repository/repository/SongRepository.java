package com.example.demo.repository.repository;

import com.example.demo.repository.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(String title, String artist);
}
