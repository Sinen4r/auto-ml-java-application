package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller 
@RequestMapping("/api/files")
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;
    String paths="C:\\Users\\PCS\\eclipse-workspace\\webInterface\\data";
    @RequestMapping("/")
    public String showUploadForm() {
        return "login";  // This will render the HTML form
    };
    @PostMapping("/")
    public String login(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            Model model
    ) {
        // Check if the name and password are correct
        if ("test".equals(name) && "test".equals(password)) {
            // If credentials are correct, display the customer page
            return "customer"; // This will render the customer.html page
        } else {
            // If credentials are incorrect, display an error message and redirect to login
            model.addAttribute("error", "Invalid credentials, please try again.");
            return "login"; // This will render the login.html page
        }
    }
    ;
    @GetMapping("/upload")
    public String showUploadPage() {
        return "customer";  // Serve the upload page when visiting "/upload"
    }
    @PostMapping("/upload")
   
    public String uploadFile(
            @RequestParam("name") String name,
            @RequestParam("target") String target,
            @RequestParam("analysis") String analysis,
            @RequestParam("file") MultipartFile file,
            Model model
) {

        if (file.isEmpty()) {
            model.addAttribute("message", "File empty : " );

            return "customer";
        }

        try {
            // Ensure the upload directory exists
            File directory = new File(paths);
            if (!directory.exists()) {
                directory.mkdirs(); // Create directory if not exists
            }

            // Use Paths.get() to ensure correct path formation
            Path path = Paths.get(paths, file.getOriginalFilename());
            System.out.println(path);
            // Save the file to the target location
            file.transferTo(path.toFile());
            model.addAttribute("message", "File uploaded successfully: " + path.toAbsolutePath());
            return "customer";
        } catch (IOException e) {
        	model.addAttribute("Error saving file: " + e.getMessage());
            return "customer";
        }
    }
}
