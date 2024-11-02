package com.example.open_edu_sim.repository;

import com.example.open_edu_sim.generics.GenericRepository;
import com.example.open_edu_sim.model.Simulado;
import com.example.open_edu_sim.usuario.Usuario;

import java.util.List;

public interface SimuladoRepository extends GenericRepository<Simulado> {
    List<Simulado> findAllByUsuarioIdOrderByYearAscDiaAscIdDesc(Long id);

    Simulado getByIdAndUsuarioId(Long id, Long usuarioId);

    boolean existsByUsuarioAndYear(Usuario usuario, Integer year);
}
