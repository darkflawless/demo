package com.example.demo.controller;


import com.example.demo.model.Song;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/searchSongs")
    public List<Song> searchSongs(@RequestParam("query") String query) {
        return songRepository.findByNameContainingIgnoreCase(query);
    }

    @GetMapping("/randomSong")
    public ResponseEntity<Song> getRandomSong() {
        Song randomSong = songRepository.findRandomSong();
        return ResponseEntity.ok(randomSong);
    }
}

