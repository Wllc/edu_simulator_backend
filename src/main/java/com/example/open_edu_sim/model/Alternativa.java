package com.example.open_edu_sim.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Table(name = "alternativas")
//public class Alternativa {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "questao_id", nullable = false)
//    @JsonBackReference
//    private Questao questao;
//
//    @Column(columnDefinition = "TEXT")
//    private String texto;
//
//    private Boolean isCorreta;
//
//    private String letraAlternativa;
//}

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "alternativas")
public class Alternativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "questao_id")
    private Questao questao;

    private Character letter;
    private String text;
    private String file;
    private Boolean isCorrect;

    // Getters e Setters
}
