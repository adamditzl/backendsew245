//(Representational State Transfer)
package com.example.demo.repository.repository;

import com.example.demo.repository.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    Page<Song> findByTitleContainingIgnoreCaseOrArtist_NameContainingIgnoreCase(String title, String artistName, Pageable pageable);
}


 /*
Alternative wo die Methode nicht aus dem Namen genommen wird sondern
 @Query("SELECT s FROM Song s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.artist) LIKE LOWER(CONCAT('%', :query, '%'))")
List<Song> searchByTitleOrArtist(@Param("query") String query);
  */