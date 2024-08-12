package com.project.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.practice.model.Signup;

@Repository
public interface SignupRepository extends JpaRepository<Signup, Long> {
    Signup findByPhoneNumber(String phoneNumber);
    Signup findByEmail(String email);
}