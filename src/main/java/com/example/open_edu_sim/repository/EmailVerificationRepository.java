package com.example.open_edu_sim.repository;

import com.example.open_edu_sim.generics.GenericRepository;
import com.example.open_edu_sim.model.auth.EmailVerification;

public interface EmailVerificationRepository extends GenericRepository<EmailVerification> {
    EmailVerification findByVerificationToken(String token);
}
