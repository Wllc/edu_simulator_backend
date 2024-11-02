package com.example.open_edu_sim.controller;

import com.example.open_edu_sim.generics.GenericRestController;
import com.example.open_edu_sim.model.QuestaoComRespostaDTO;
import com.example.open_edu_sim.model.Resposta;
import com.example.open_edu_sim.model.RespostaDTO;
import com.example.open_edu_sim.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respostas")
public class RespostaController{

    @Autowired
    private RespostaService service;


    @PostMapping
    public Resposta insert(@RequestBody Resposta t) {
        return service.update(t);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Resposta> findById(@PathVariable Long id) {
        Resposta t = service.getById(id);
        if(t == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(t);
    }

    @GetMapping
    public ResponseEntity<List<Resposta>> findAll() {
        List<Resposta> t = service.findAll();
        if(t == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(t);
    }

    @PutMapping()
    public ResponseEntity<Resposta> update(@RequestBody Resposta t) {
        service.update(t);
        return ResponseEntity.ok().body(t);
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Resposta t = service.getById(id);
        if(t == null){
            return ResponseEntity.notFound().build();
        }
        service.delete(t);
        return ResponseEntity.ok().build();
    }




}
