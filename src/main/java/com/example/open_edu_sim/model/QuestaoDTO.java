package com.example.open_edu_sim.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class QuestaoDTO {
    private Long id;
    private String prova;
    private String cabecalho;
    private String declaracao;
    private Boolean isImagem;
    private List<AlternativaDTO> alternativas;
}
