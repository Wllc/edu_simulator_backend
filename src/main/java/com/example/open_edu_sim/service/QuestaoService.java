package com.example.open_edu_sim.service;

import com.example.open_edu_sim.generics.GenericService;
import com.example.open_edu_sim.model.Questao;
import com.example.open_edu_sim.model.QuestaoComRespostaDTO;
import com.example.open_edu_sim.model.Resposta;
import com.example.open_edu_sim.repository.QuestaoRepository;
import com.example.open_edu_sim.repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestaoService extends GenericService<Questao> {

    private static final Logger log = LoggerFactory.getLogger(QuestaoService.class);

    @Autowired
    private QuestaoRepository repository;
    public Page<Questao> findByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    public Page<QuestaoComRespostaDTO> buscarQuestoesComRespostas(Long usuarioId, Long simuladoId, int ano, int page, int size, boolean finalizado, int dia) {
        log.debug("Buscando questões com respostas para usuário {}, simulado {}, ano {}, página {}, tamanho {}",
                usuarioId, simuladoId, ano, page, size);

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("index"));

        Page<Questao> questoesPage = questaoRepository.findByYearAndDia(ano, dia, pageRequest);
        log.debug("Encontradas {} questões", questoesPage.getTotalElements());

        List<QuestaoComRespostaDTO> questaoComRespostas = questoesPage.stream()
                .map(questao -> {
                    log.debug("Processando questão {}", questao.getId());
                    Resposta resposta = respostaRepository.findBySimuladoIdAndQuestaoIdAndUsuarioId(simuladoId, questao.getId(), usuarioId);
                    log.debug("Resposta encontrada: {}", resposta != null ? resposta.getId() : "nenhuma");
                    log.debug("Arquivos da questão: {}", questao.getFiles() != null ? questao.getFiles().size() : "nenhum");
                    QuestaoComRespostaDTO dto = new QuestaoComRespostaDTO(questao, resposta);
                    if(finalizado) {
                        dto.setCorrectAlternative(questao.getCorrectAlternative());
                    }
                    log.debug("DTO criado: {}", dto);
                    return dto;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(questaoComRespostas, pageRequest, questoesPage.getTotalElements());
    }

//    public Page<QuestaoComRespostaDTO> buscarQuestoesComRespostas(Long usuarioId, Long simuladoId, int ano, int page, int size) {
//        // Define a paginação
//        PageRequest pageRequest = PageRequest.of(page - 1, size);
//
//        // Busca as questões paginadas para o simulado
//        Page<Questao> questoesPage = questaoRepository.findByYear(ano, pageRequest);
//
//        // Para cada questão, buscar a resposta do usuário
//        List<QuestaoComRespostaDTO> questaoComRespostas = questoesPage.stream()
//                .map(questao -> {
//                    Resposta resposta = respostaRepository.findBySimuladoIdAndQuestaoIdAndUsuarioId(simuladoId, questao.getId(), usuarioId);
//                    return new QuestaoComRespostaDTO(questao, resposta);
//                })
//                .collect(Collectors.toList());
//
//        // Retorna a página de questões com respostas
//        return new PageImpl<>(questaoComRespostas, pageRequest, questoesPage.getTotalElements());
//    }

//    public List<Questao> findRandomDistinctQuestoes() {
//        return repository.findRandomDistinctQuestoes();
//    }
}
