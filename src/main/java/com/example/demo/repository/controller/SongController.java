package com.example.demo.repository.controller;

import com.example.demo.repository.entity.Song;
import com.example.demo.repository.entity.Artist;
import com.example.demo.repository.SongService;
import com.example.demo.repository.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:5173") // Allows Cross-Origin requests from the frontend application
public class SongController {
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }
    @Autowired
    private ArtistRepository artistRepository; // Inject ArtistRepository

    // Get all songs GET
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    // Get song by ID GET
    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    @PostMapping
    public Song createSong(@RequestBody Song song) {
        if (song.getTitle() == null || song.getGenre() == null || song.getLength() == null || song.getArtistId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }
        return songService.saveSong(song);
    }

    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        return songService.updateSong(id, updatedSong, updatedSong.getArtistId()); // Get artistId from updatedSong
    }



    // Delete a song by ID DELETE
    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
    }

    // Search for songs based on a query
    @GetMapping("/search")
    public List<Song> searchSongs(@RequestParam String query) {
        return songService.searchSongs(query);
    }
}
