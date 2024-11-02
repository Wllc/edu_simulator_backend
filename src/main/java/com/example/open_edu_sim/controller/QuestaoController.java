package com.example.open_edu_sim.controller;

import com.example.open_edu_sim.generics.GenericRestController;
import com.example.open_edu_sim.model.Questao;
import com.example.open_edu_sim.model.QuestaoComRespostaDTO;
import com.example.open_edu_sim.service.QuestaoService;
import com.example.open_edu_sim.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questoes")
public class QuestaoController {

    @Autowired
    private QuestaoService questaoService;

    @GetMapping("/")
    public ResponseEntity<Page<QuestaoComRespostaDTO>> buscarQuestoesComRespostas(
            Authentication authentication,
            @RequestParam Long simuladoId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam int ano,
            @RequestParam(defaultValue = "1") int size,
            @RequestParam(defaultValue = "false") boolean finalizado,
            @RequestParam int dia) {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        Page<QuestaoComRespostaDTO> questoesComRespostas = questaoService.buscarQuestoesComRespostas(usuario.getId(), simuladoId, ano, page, size, finalizado, dia);
        return ResponseEntity.ok(questoesComRespostas);
    }
}
