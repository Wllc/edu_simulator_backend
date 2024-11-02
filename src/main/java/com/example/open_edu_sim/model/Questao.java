package com.example.open_edu_sim.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "questoes")
public class Questao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer index;
    private Integer year;
    private Integer dia;
    private String language;
    private String discipline;


    private String context;

    private Character correctAlternative;


    private String alternativesIntroduction;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL)
    @OrderBy("letter ASC")
    private List<Alternativa> alternativas;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Arquivo> files;


    // Getters e Setters
}


