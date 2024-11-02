package com.example.open_edu_sim.repository;

import com.example.open_edu_sim.generics.GenericRepository;
import com.example.open_edu_sim.model.auth.PasswordResetToken;

import java.util.Optional;

public interface TokenRepository extends GenericRepository<PasswordResetToken> {

    Optional<PasswordResetToken> findByToken(String token);
}
