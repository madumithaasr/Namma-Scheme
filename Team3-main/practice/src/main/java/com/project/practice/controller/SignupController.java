package com.project.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.practice.model.Signup;
import com.project.practice.service.SignupService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/signup")
@CrossOrigin(origins = "http://localhost:3000")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @PostMapping
    public ResponseEntity<?> createSignup(@RequestBody Signup signup) {
        if (signupService.phoneNumberExists(signup.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Phone number already exists"));
        }
        if (signupService.emailExists(signup.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "Email already exists"));
        }

        Signup newSignup = signupService.createSignup(signup);
        return ResponseEntity.ok(Map.of("success", true, "message", "Registration successful", "signup", newSignup));
    }

    @GetMapping
    public List<Signup> getAllSignups() {
        return signupService.getAllSignups();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Signup> getSignupById(@PathVariable Long id) {
        return signupService.getSignupById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignup(@PathVariable Long id) {
        if (signupService.getSignupById(id).isPresent()) {
            signupService.deleteSignup(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkSignup(@RequestBody Signup signup) {
        Signup existingSignup = signupService.findByPhoneNumber(signup.getPhoneNumber());
        if (existingSignup != null && existingSignup.getPassword().equals(signup.getPassword())) {
            if ("admin".equals(existingSignup.getPhoneNumber())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login successful!");
                response.put("isAdmin", true);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.ok(Map.of("success", true, "message", "Login successful!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "Invalid phone number or password"));
        }
    }
}