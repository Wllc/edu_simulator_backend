package com.example.open_edu_sim.controller;

import com.example.open_edu_sim.generics.GenericRestController;
import com.example.open_edu_sim.model.Alternativa;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alternativas")
public class AlternativaController extends GenericRestController<Alternativa> {
}
