//(Representational State Transfer)
package com.example.demo.repository.controller;

import com.example.demo.repository.entity.Song;
import com.example.demo.repository.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:5173")
// Allows Cross-Origin requests from the frontend application
public class SongController {

    @Autowired
    private SongService songService;

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

    // Create a new song POST
    @PostMapping
    public Song createSong(@RequestBody Song song) {
        return songService.saveSong(song);
    }

    @PutMapping("/{id}") // Update a song PUT
    public Song updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        return songService.updateSong(id, updatedSong);
    }

    // Delete a song by ID DELETE
    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
    }

    @GetMapping("/search")
    public List<Song> searchSongs(@RequestParam String query) {
        return songService.searchSongs(query);
    }


}
