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
import java.util.List;

import org.machine.mavensample1.machine;




@Controller 
@RequestMapping("/api/files")
@SessionAttributes("uploadData") 
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
    public String uploafdFile(
            @RequestParam("name") String name,
            @RequestParam("target") String target,
            @RequestParam("typePred") String Task,
            @RequestParam("analysis") String analysis,
            @RequestParam("file") MultipartFile file,
            Model model
    ) {
        if (file.isEmpty()) {
            model.addAttribute("message", "File is empty.");
            return "customer";
        }

        try {
            // Save the file
            File directory = new File(paths);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Path path = Paths.get(paths, file.getOriginalFilename());
            file.transferTo(path.toFile());

            // Save the data in the session
            UploadData uploadData = new UploadData();
            uploadData.setName(name);
            uploadData.setTarget(target);
            uploadData.setAnalysis(analysis);
            uploadData.setFilePath(path.toAbsolutePath().toString());
            uploadData.setTask(Task);

            model.addAttribute("uploadData", uploadData);
            model.addAttribute("message", "File uploaded successfully.");
            return "customer";
        } catch (IOException e) {
            model.addAttribute("message", "Error saving file: " + e.getMessage());
            return "customer";
        }
    };
    @PostMapping("/generate")
    public String generateImage(Model model) throws IOException {
        // Retrieve the uploaded data from the session
        UploadData uploadData = (UploadData) model.getAttribute("uploadData");

        if (uploadData == null) {
            model.addAttribute("prediction", "No data available to generate. Please upload a file first.");
            return "customer";
        }
        System.out.println("done prediction");

        // Perform the generate action using the stored data
        List<String> prediction = machine.Predict(uploadData.getFilePath(),uploadData.getTask(), uploadData.getTarget());
        System.out.println("done prediction");
        model.addAttribute("prediction",  prediction);
        System.out.println(prediction);
        return "customer";
    }}
