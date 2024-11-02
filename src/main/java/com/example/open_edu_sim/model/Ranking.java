package com.example.open_edu_sim.model;

import com.example.open_edu_sim.usuario.Usuario;

import java.util.List;

public record Ranking(PosicaoUsuario posicaoUsuario, List<UsuarioDTO> ranking) {
}
