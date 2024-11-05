package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String register(Model model) {
        return "register"; // This should match the name of your HTML file in the templates folder
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("pass") String pass,
                                 Model model) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPass(pass);

        userRepository.save(newUser);

        model.addAttribute("message", "User registered successfully");
        return "register"; // This should match the name of your HTML file in the templates folder
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // Tên file HTML trong thư mục templates
    }

    // Xử lý form đăng nhập
    @PostMapping("/login")
    public String loginSubmit(@RequestParam("email") String email,
                              @RequestParam("pass") String pass,
                              Model model) {
        try {
            User existingUser = userRepository.findByEmailAndPass(email, pass);

            if (existingUser != null) {
                model.addAttribute("message", "Login successful");
                return "redirect:/songs"; // Trang hiển thị danh sách bài hát
            } else {
                model.addAttribute("message", "Invalid email or password");
                return "redirect:/login"; // Quay lại trang đăng nhập nếu thất bại
            }
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred: " + e.getMessage());
            return "redirect:/login"; // Quay lại trang đăng nhập nếu có lỗi
        }
    }
}
