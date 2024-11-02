package com.example.open_edu_sim.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioCreateDTO {
    private String nome;
    private String email;
    private boolean twoFactorAuthenticationEnabled;
}
