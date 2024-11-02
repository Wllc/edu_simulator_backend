package com.example.open_edu_sim.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
//@Data
//public class AlternativaDTO {
//    private Long id;
//    private String texto;
//    private String letraAlternativa;
//}

@Getter
@Setter
@Data
public class AlternativaDTO {
    private Long id;
    private Character letra;
    private String texto;
    private String arquivo;

    public AlternativaDTO(Alternativa alternativa) {
        this.id = alternativa.getId();
        this.letra = alternativa.getLetter();
        this.texto = alternativa.getText();
        this.arquivo = alternativa.getFile();
    }

    // Getters e Setters
}
