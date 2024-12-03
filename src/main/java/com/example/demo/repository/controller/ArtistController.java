package com.example.demo.repository.controller;

import com.example.demo.repository.ArtistService;
import com.example.demo.repository.entity.Artist;
import com.example.demo.repository.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@CrossOrigin(origins = "http://localhost:5173")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/{id}")
    public Artist getArtistbyID(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @PostMapping
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.saveArtist(artist);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
    }
}
