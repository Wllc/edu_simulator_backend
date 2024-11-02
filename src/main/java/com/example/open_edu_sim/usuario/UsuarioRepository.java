package com.example.open_edu_sim.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);

    Page<Usuario> findAllByOrderByPontuacaoTotalDesc(Pageable pageable);

//    @Query("SELECT u.pontuacaoTotal FROM Usuario u WHERE u.login = ?1")
//    Integer findPontuacaoTotalPorLogin(String login);
//
//    Long countByPontuacaoTotalGreaterThanEqual(Integer pontuacao);
}
