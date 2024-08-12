package com.project.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.practice.model.Signup;
import com.project.practice.repository.SignupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SignupService {

    @Autowired
    private SignupRepository signupRepository;

    public Signup createSignup(Signup signup) {
        signup.setActive(true);
        return signupRepository.save(signup);
    }

    public List<Signup> getAllSignups() {
        return signupRepository.findAll();
    }

    public Optional<Signup> getSignupById(Long id) {
        return signupRepository.findById(id);
    }

    public void deleteSignup(Long id) {
        signupRepository.deleteById(id);
    }

    public Signup findByPhoneNumber(String phoneNumber) {
        return signupRepository.findByPhoneNumber(phoneNumber);
    }

    public Signup findByEmail(String email) {
        return signupRepository.findByEmail(email);
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return signupRepository.findByPhoneNumber(phoneNumber) != null;
    }

    public boolean emailExists(String email) {
        return signupRepository.findByEmail(email) != null;
    }
}