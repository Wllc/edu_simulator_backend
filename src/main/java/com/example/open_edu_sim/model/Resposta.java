package com.example.open_edu_sim.model;

import com.example.open_edu_sim.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "respostas")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "simulado_id")
    @JsonBackReference
    private Simulado simulado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference("neymar")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "questao_id")
    @JsonBackReference("boa tarde")
    private Questao questao;

    private Character alternativaSelecionada;

    // Getters e Setters
}
