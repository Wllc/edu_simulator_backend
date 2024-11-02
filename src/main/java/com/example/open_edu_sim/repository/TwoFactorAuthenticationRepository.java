package com.example.open_edu_sim.repository;

import com.example.open_edu_sim.generics.GenericRepository;
import com.example.open_edu_sim.model.auth.TwoFactorAuthentication;
import com.example.open_edu_sim.usuario.Usuario;

public interface TwoFactorAuthenticationRepository extends GenericRepository<TwoFactorAuthentication> {
    TwoFactorAuthentication findByUsuario(Usuario usuario);
}
