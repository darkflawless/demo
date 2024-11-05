package com.example.demo.controller;

import com.example.demo.model.Image;
import com.example.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/upload")
    public String uploadImage() {
        return "upload";
    }

    @GetMapping("/media")
    public String getMedia(Model model) {
        List<Image> images = imageRepository.findAll();
        model.addAttribute("images", images);
        return "media";
    }

    @PostMapping("/uploadImage")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            String fileName = saveFile(file);

            Image image = new Image();
            image.setFileName(fileName);
            image.setFilePath("uploads/" + fileName);
            image.setUploadDate(LocalDate.now());

            imageRepository.save(image);

            redirectAttributes.addFlashAttribute("message", "File uploaded successfully: " + fileName);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to upload file");
        }

        return "upload";
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