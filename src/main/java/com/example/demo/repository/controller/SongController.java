package com.example.demo.repository.controller;

import com.example.demo.repository.entity.Song;
import com.example.demo.repository.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    // Get all songs
    @GetMapping("/songs")
    public Page<Song> getAllSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        System.out.println("Page: " + page + ", Size: " + size); // Debugging
        Pageable pageable = PageRequest.of(page, size);
        return songService.getAllSongs(pageable);
    }

    // Get song by ID
    @GetMapping("/songs/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    // Search for songs with query and pagination
    @GetMapping("/songs/search")
    public Page<Song> searchSongs(@RequestParam String query,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return songService.searchSongs(query, pageable);
    }

    // Create a new song
    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song createdSong = songService.saveSong(song);
        return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
    }

    /*
    // Update a song
    @PutMapping("/songs/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        return songService.updateSong(id, updatedSong);
    }
*/
    // Delete a song
    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}