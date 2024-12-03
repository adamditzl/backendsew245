package com.example.demo.repository;

import com.example.demo.repository.entity.Artist;
import com.example.demo.repository.entity.Song;
import com.example.demo.repository.repository.ArtistRepository;
import com.example.demo.repository.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;
    private SongService songService;

    // Retrieve all songs
    public List<Song> getAllSongs() {
        return songRepository.findAll();
}

    // Retrieve a song by its ID
    public Song getSongById(Long id) {
        return songRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Song createSong(@RequestBody Song song) {
        if (song.getTitle() == null || song.getGenre() == null || song.getLength() == null || song.getArtistId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }
        return songService.saveSong(song);
    }


    // Save a new song with artist ID
    public Song saveSong(Song song) {  // artistId als Parameter hinzugefügt
        Artist artist = artistRepository.findById(song.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        song.setArtist(artist); // Ensure the artist entity is set
        return songRepository.save(song);
    }

    // Update an existing song with artist ID
    public Song updateSong(Long id, Song updatedSong, Long artistId) {  // artistId als Parameter hinzugefügt
        Optional<Song> optionalSong = songRepository.findById(id);
        if (optionalSong.isPresent()) {
            Song existingSong = optionalSong.get();
            existingSong.setTitle(updatedSong.getTitle());

            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new RuntimeException("Artist not found"));
            existingSong.setArtist(artist);

            existingSong.setGenre(updatedSong.getGenre());
            existingSong.setLength(updatedSong.getLength());
            return songRepository.save(existingSong);
        }
        return null; // or throw an exception if preferred
    }

    // Delete a song by its ID
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    // Search for songs by title or artist name
    public List<Song> searchSongs(String query) {
        return songRepository.findByTitleContainingIgnoreCaseOrArtist_NameContainingIgnoreCase(query, query);
    }
}
