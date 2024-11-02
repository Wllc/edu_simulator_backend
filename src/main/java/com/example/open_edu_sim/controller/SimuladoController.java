package com.example.open_edu_sim.controller;

import com.example.open_edu_sim.generics.GenericRestController;
import com.example.open_edu_sim.model.*;
import com.example.open_edu_sim.service.QuestaoService;
import com.example.open_edu_sim.service.RespostaService;
import com.example.open_edu_sim.service.SimuladoService;
import com.example.open_edu_sim.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/simulados")
public class SimuladoController {

    @Autowired
    private SimuladoService simuladoService;

    @GetMapping
    public ResponseEntity<List<Simulado>> findAll(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        List<Simulado> t = simuladoService.findAllByUsuarioId(usuario.getId());
        if (t == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(t);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Simulado simulado = simuladoService.getByIdAndUsuarioId(id, usuario.getId());

        if (simulado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(simulado);

//        if (simulado.getFinalizado()) {
//            return ResponseEntity.ok().body(simulado);
//        } else {
////            SimuladoDTO simuladoDTO = convertToDTO(simulado);
//            return ResponseEntity.ok().body(simulado);
//        }
    }

    @PutMapping("/{simuladoId}")
    public ResponseEntity<Simulado> finalizarSimulado(Authentication authentication,
            @PathVariable Long simuladoId) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Simulado simuladoFinalizado = simuladoService.finalizarSimulado(usuario.getId(), simuladoId);
        return ResponseEntity.ok(simuladoFinalizado);
    }

    @PostMapping
    public ResponseEntity<Simulado> criarSimulado(Authentication authentication, @RequestParam Integer ano, @RequestParam Integer dia) {
        Usuario usuario =(Usuario) authentication.getPrincipal();
        Simulado simuladoNovo = simuladoService.iniciarSimulado(usuario, ano, dia);
        return ResponseEntity.ok(simuladoNovo);
    }
}
