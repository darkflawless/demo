package com.example.demo.controller;


import com.example.demo.model.Song;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.List;


@Controller
public class RecommendSongController {
    @Autowired
    private SongRepository songRepository;

//    @GetMapping("/recommend-songs")
//    public String getRecommendSongs(Model model) {
//        List<Song> songs = songRepository.findTop2ByOrderByViewAsc();
//        model.addAttribute("rec", songs);
//        return "songs";
//    }
}