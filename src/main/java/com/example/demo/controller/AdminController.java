package com.example.demo.controller;

import com.example.demo.model.Song;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/admin")
    public String getAllSongs(Model model) {
        List<Song> songs = songRepository.findAll();
        model.addAttribute("songs", songs);
        return "admin";
    }

    @PostMapping("/admin")
    public String addSong(@RequestParam("name") String name,
                          @RequestParam("author") String author,
                          @RequestParam("link") String url,
                          Model model) {
        Song newSong = new Song();

        newSong.setFileName(name);
        newSong.setAuthor(author);
        newSong.setLink(url);

        songRepository.save(newSong);

        model.addAttribute("message", "Song added successfully");
        return "admin";
    }
}