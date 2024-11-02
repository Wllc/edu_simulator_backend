package com.example.open_edu_sim.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Data
public class SimuladoDTO {
    private Long id;
    private UsuarioDTO usuario;
    private Boolean finalizado;
    private Time temporizador;
    private Timestamp dataInicio;
    private List<QuestaoDTO> questoes;
    private List<RespostaDTO> respostas;
}
