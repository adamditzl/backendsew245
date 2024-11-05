package com.example.demo.repository.repository;

import com.example.demo.repository.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    // Additional query methods for Artist can be added here if needed
}
