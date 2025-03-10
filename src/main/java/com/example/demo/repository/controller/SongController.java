package com.example.demo.repository.controller;

import com.example.demo.repository.ArtistService;
import com.example.demo.repository.entity.Song;
import com.example.demo.repository.SongService;
import com.example.demo.repository.entity.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

  // Frontend-URL
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")  // Frontend-URL
public class SongController {

    private final SongService songService;
    private final ArtistService artistService;  // Inject the ArtistService instance

    @Autowired
    public SongController(SongService songService, ArtistService artistService) {
        this.songService = songService;
        this.artistService = artistService;  // Here is where we inject the ArtistService
    }

    @PostMapping(value = "/songs/more", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Song> createSongFromJsonOrMultipart(
            @RequestParam(required = true) String title,
            @RequestParam(required = false) Long artistId,  // Artist ID
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Long length,
            @RequestParam(required = false) MultipartFile file,
            @RequestBody(required = false) Song songFromJson) throws IOException {

        Song song;

        // If JSON is provided, use the JSON object
        if (songFromJson != null) {
            song = songFromJson;
        } else if (title != null && genre != null && length != null && file != null && artistId != null) {
            song = new Song();
            song.setTitle(title);
            song.setGenre(genre);
            song.setLength(length);
            song.setData(file.getBytes());

            // Use the artistService to get the artist by ID
            Artist artist = artistService.getArtistById(artistId);  // Correctly using the artistService

            if (artist != null) {
                song.setArtist(artist);  // Set the artist for the song
            } else {
                return ResponseEntity.badRequest().body(null);  // Return error if artist not found
            }
        } else {
            return ResponseEntity.badRequest().body(null);  // Return error if data is missing
        }

        Song savedSong = songService.saveSong(song);
        return new ResponseEntity<>(savedSong, HttpStatus.CREATED);
    }
}
