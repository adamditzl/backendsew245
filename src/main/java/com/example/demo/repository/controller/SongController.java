package com.example.demo.repository.controller;

import com.example.demo.repository.entity.Song;
import com.example.demo.repository.entity.Artist;
import com.example.demo.repository.SongService;
import com.example.demo.repository.repository.ArtistRepository;
import com.example.demo.repository.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Allows Cross-Origin requests from the frontend application
public class SongController {
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }
    @Autowired
    private ArtistRepository artistRepository; // Inject ArtistRepository
private SongRepository SongRepository;
    // Get all songs GET
    @GetMapping("/songs")
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    // Get song by ID GET
    @GetMapping("/songs/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }
    @PostMapping("/songs")
    public ResponseEntity<?> createSong(@RequestBody Song song) {

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+song.getArtistId());
        // Überprüfe, ob alle Felder korrekt sind
        if (song.getTitle() == null || song.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Title is required");
        }
        if (song.getGenre() == null || song.getGenre().isEmpty()) {
            return ResponseEntity.badRequest().body("Genre is required");
        }
        if (song.getLength() == null || song.getLength() <= 0) {
            return ResponseEntity.badRequest().body("Length should be greater than 0");
        }

        // Stelle sicher, dass der Künstler mit der artistId existiert
        Artist artist = artistRepository.findById(song.getArtistId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist not found"));
        System.out.println("???"+artist);
        song.setArtist(artist);

        // Speichern des Songs
        return ResponseEntity.ok(songService.saveSong(song));
    }


    @PutMapping("/songs/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        return songService.updateSong(id, updatedSong, updatedSong.getArtistId()); // Get artistId from updatedSong
    }



    // Delete a song by ID DELETE
    @DeleteMapping("/songs/{id}")
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
    }

    // Search for songs based on a query
    @GetMapping("/songs/search")
    public List<Song> searchSongs(@RequestParam String query) {
        return songService.searchSongs(query);
    }

    public Song saveSong(Song song) {
        try {
            return SongRepository.save(song);
        } catch (Exception e) {
          //  logger.error("Error saving song", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving song", e);
        }
    }

}
