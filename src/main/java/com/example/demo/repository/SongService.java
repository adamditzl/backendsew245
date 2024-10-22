//(Representational State Transfer)
package com.example.demo.repository;

import com.example.demo.repository.entity.Song;
import com.example.demo.repository.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    // Retrieve all songs
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // Retrieve a song by its ID
    public Song getSongById(Long id) {
        return songRepository.findById(id).orElse(null);
    }

    // Save a new song
    public Song saveSong(Song song) {
        return songRepository.save(song);
    }

    // Update an existing song
    public Song updateSong(Long id, Song updatedSong) {
        Optional<Song> optionalSong = songRepository.findById(id);
        if (optionalSong.isPresent()) {
            Song existingSong = optionalSong.get();
            existingSong.setTitle(updatedSong.getTitle());
            existingSong.setArtist(updatedSong.getArtist());
            existingSong.setGenre(updatedSong.getGenre());
            existingSong.setLength(updatedSong.getLength());
            return songRepository.save(existingSong);
        }
        return null; // or throw an exception if you prefer
    }

    // Delete a song by its ID
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    public List<Song> searchSongs(String query) {
        return songRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(query, query);
    }

}
