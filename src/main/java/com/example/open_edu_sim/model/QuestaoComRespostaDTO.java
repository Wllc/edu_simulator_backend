package com.example.open_edu_sim.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
@Setter
@Getter
@Data
public class QuestaoComRespostaDTO {

    private Long id;
    private String titulo;
    private RespostaDTO respostaSelecionada = new RespostaDTO();
    private String language;
    private String discipline;
    private String context;
    private String alternativesIntroduction;
    private List<Arquivo> files;
    private List<AlternativaDTO> alternativas;
    private Character correctAlternative;


    public QuestaoComRespostaDTO(Questao questao, Resposta resposta) {
        this.id = questao.getId();
        this.files = questao.getFiles();
        this.language = questao.getLanguage();
        this.discipline = questao.getDiscipline();
        this.context = questao.getContext();
        this.alternativesIntroduction = questao.getAlternativesIntroduction();
        this.titulo = questao.getTitle();
        this.alternativas = questao.getAlternativas().stream()
                .map(alternativa -> new AlternativaDTO(alternativa))
                .collect(Collectors.toList());
        if(resposta != null) {
            this.respostaSelecionada.setId(resposta.getId());
            this.respostaSelecionada.setAlternativaSelecionada(resposta.getAlternativaSelecionada());
        }
    }

    @Override
    public String toString() {
        return "QuestaoComRespostaDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", respostaSelecionada=" + respostaSelecionada +
                ", language='" + language + '\'' +
                ", discipline='" + discipline + '\'' +
                ", context='" + context + '\'' +
                ", alternativesIntroduction='" + alternativesIntroduction + '\'' +
                ", files=" + (files != null ? files.size() : 0) + " arquivos" +
                ", alternativas=" + (alternativas != null ? alternativas.size() : 0) + " alternativas" +
                '}';
    }

    // Getters e Setters
}
