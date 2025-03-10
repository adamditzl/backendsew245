package com.example.demo.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Arrays;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Titel darf nicht leer sein") // Titel darf nicht null oder leer sein
    private String title;

    @NotBlank(message = "Genre darf nicht leer sein") // Genre darf nicht null oder leer sein
    private String genre;

    @Min(value = 1, message = "Länge muss mindestens 1 Sekunde betragen") // Länge muss mindestens 1 sein
    private Long length;

    @Lob
    @NotNull(message = "Audiodatei darf nicht null sein") // Daten dürfen nicht null sein
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    @NotNull(message = "Künstler darf nicht null sein") // Der Künstler darf nicht null sein
    private Artist artist;

    // Getters und Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
