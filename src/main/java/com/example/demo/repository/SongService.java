package com.example.demo.repository;

import com.example.demo.repository.entity.Song;
import com.example.demo.repository.entity.Artist;

import com.example.demo.repository.repository.ArtistRepository;
import com.example.demo.repository.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public SongService(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    public Page<Song> getAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    public Page<Song> searchSongs(String query, Pageable pageable) {
        return songRepository.findByTitleContainingIgnoreCaseOrArtist_NameContainingIgnoreCase(query, query, pageable);
    }

    public Song saveSong(Song song) {
        return songRepository.save(song);
    }
}
