package com.example.demo.repository.controller;

import com.example.demo.repository.ArtistService;
import com.example.demo.repository.entity.Song;
import com.example.demo.repository.SongService;
import com.example.demo.repository.entity.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")  // Frontend-URL
public class SongController {

    private final SongService songService;
    private final ArtistService artistService;

    @Autowired
    public SongController(SongService songService, ArtistService artistService) {
        this.songService = songService;
        this.artistService = artistService;
    }

    @PostMapping(value = "/songs/more", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Song> createSongFromJsonOrMultipart(
            @RequestParam(required = true) String title,
            @RequestParam(required = false) Long artistId,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Long length,
            @RequestParam(required = false) String file,
            @RequestBody(required = false) Song songFromJson) throws IOException {

        Song song;

        // Wenn JSON übermittelt wurde, nutzen wir das JSON-Objekt
        if (songFromJson != null) {
            song = songFromJson;
        } else if (title != null && genre != null && length != null && file != null && artistId != null) {
            song = new Song();
            song.setTitle(title);
            song.setGenre(genre);
            song.setLength(length);

            // MP3-Datei in Base64 umwandeln
            String base64Audio = Base64.getEncoder().encodeToString(file.getBytes());
            song.setData("data:audio/mp3;base64," + base64Audio);  // Base64-codierte Data URL setzen

            // Künstler mit der artistId abrufen
            Artist artist = artistService.getArtistById(artistId);

            if (artist != null) {
                song.setArtist(artist);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.badRequest().body(null);
        }

        // Speichern und zurückgeben
        Song savedSong = songService.saveSong(song);
        return new ResponseEntity<>(savedSong, HttpStatus.CREATED);
    }

    @GetMapping("/songs")
    public Page<Song> getAllSongs(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return songService.getAllSongs(pageable);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        Song song = songService.getSongById(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(song);
    }

    @GetMapping("/songs/search")
    public Page<Song> searchSongs(@RequestParam String query,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return songService.searchSongs(query, pageable);
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
