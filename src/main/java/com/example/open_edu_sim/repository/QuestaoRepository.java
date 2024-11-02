package com.example.open_edu_sim.repository;

import com.example.open_edu_sim.generics.GenericRepository;
import com.example.open_edu_sim.model.Questao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestaoRepository extends GenericRepository<Questao> {
        Page<Questao> findByYearAndDia(Integer ano,Integer dia, Pageable pageable);
}
