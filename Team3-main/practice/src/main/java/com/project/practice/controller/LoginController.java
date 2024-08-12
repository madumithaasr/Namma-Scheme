package com.project.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.practice.model.Login;
import com.project.practice.model.Signup;
import com.project.practice.service.SignupService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private SignupService signupService;

    @PostMapping("/check")
    public ResponseEntity<?> checkLogin(@RequestBody Login login) {
        // Find the signup details using email
        Signup existingSignup = signupService.findByEmail(login.getEmail());

        // Check if the credentials match
        if (existingSignup != null && existingSignup.getPassword().equals(login.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful!");
            response.put("isAdmin", "admin".equals(existingSignup.getRole()));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Invalid email or password"));
        }
    }
}