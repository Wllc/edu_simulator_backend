package com.example.open_edu_sim.model;

import com.example.open_edu_sim.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "simulados")
public class Simulado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;
    private Integer dia;
    private Boolean finalizado;
    private Timestamp dataInicio;
    private Time temporizador = Time.valueOf("05:30:00");

    private Float pontuacaoHumanas = 0f;
    private Float pontuacaoLinguagens = 0f;
    private Float pontuacaoCienciasNatureza = 0f;
    private Float pontuacaoMatematica = 0f;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
//    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "simulado", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Resposta> respostas;

    // Getters e Setters
}
