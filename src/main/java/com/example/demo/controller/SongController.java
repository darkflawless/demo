package com.example.demo.controller;

import com.example.demo.model.Song;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")  // Cho phép tất cả các nguồn truy cập
@Controller
public class SongController {

    @Autowired
    private SongRepository songRepository;


    @GetMapping("/songs")
    public String getSongs(Model model) {
        List<Song> topSongs = songRepository.findTop3ByOrderByIdAsc();
        List<Song> allSongs = songRepository.findAll();
        List<Song> recommendedSongs = songRepository.findTop3Random();

        model.addAttribute("topSongs", topSongs);
        model.addAttribute("allSongs", allSongs);
        model.addAttribute("rec", recommendedSongs); // Now 'rec' will always be populated

        return "songs";
    }

    @GetMapping("/topSongs")
    @ResponseBody
    public List<Song> getTopSongs() {
        return songRepository.findTop3ByOrderByIdAsc();
    }

    @GetMapping("/randomSongs")
    @ResponseBody
    public List<Song> getRandomSongs() {
        return songRepository.findTop3Random();
    }

    @PostMapping("/uploadSong")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {

            String fileName = file.getOriginalFilename(); // Lấy tên gốc của file
            String baseName = fileName.substring(0, fileName.lastIndexOf('.')); // Lấy phần tên không có đuôi

            Song song = new Song();
            song.setLink("uploads/" + fileName); // Giữ nguyên file nhạc với đuôi gốc
            song.setFileName(fileName); // Lưu tên gốc của file nhạc
            song.setCover("uploads/" + baseName + ".gif"); // Đổi đuôi ảnh thành .jpg

            songRepository.save(song);

            redirectAttributes.addFlashAttribute("message", "File uploaded successfully: " + fileName);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to upload file");
        }

        return "redirect:/upload";
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return fileName;
    }
}