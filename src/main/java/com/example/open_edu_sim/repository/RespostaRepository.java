package com.example.open_edu_sim.repository;

import com.example.open_edu_sim.generics.GenericRepository;
import com.example.open_edu_sim.model.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RespostaRepository extends GenericRepository<Resposta> {
    Resposta findBySimuladoIdAndQuestaoIdAndUsuarioId(Long simuladoId, Long questaoId, Long usuarioId);

    List<Resposta> findBySimuladoIdAndUsuarioId(Long simuladoId, Long usuarioId);

    // Custom query to avoid N+1 problem: fetch all responses for a set of questions
    @Query("SELECT r FROM Resposta r WHERE r.simulado.id = :simuladoId AND r.usuario.id = :usuarioId AND r.questao.id IN :questaoIds")
    List<Resposta> findBySimuladoIdAndUsuarioIdAndQuestaoIdIn(@Param("simuladoId") Long simuladoId, @Param("usuarioId") Long usuarioId, @Param("questaoIds") Set<Long> questaoIds);
}
