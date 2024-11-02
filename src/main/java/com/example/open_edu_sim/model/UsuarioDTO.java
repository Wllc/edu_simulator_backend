package com.example.open_edu_sim.model;

import com.example.open_edu_sim.usuario.Usuario;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private boolean twoFactorAuthenticationEnabled;
    private Integer maiorPontuacao;
    private Integer pontuacaoTotal;

    public static List<UsuarioDTO> convert(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioDTO::convertToUsuarioDTO)
                .toList();
    }

    public static UsuarioDTO convertToUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setMaiorPontuacao(usuario.getMaiorPontuacao());
        dto.setPontuacaoTotal(usuario.getPontuacaoTotal());
        dto.setTwoFactorAuthenticationEnabled(usuario.isTwoFactorAuthenticationEnabled());
        return dto;
    }
}
