package com.example.open_edu_sim.service;

import com.example.open_edu_sim.generics.GenericService;
import com.example.open_edu_sim.model.Questao;
import com.example.open_edu_sim.model.Resposta;
import com.example.open_edu_sim.model.Simulado;
import com.example.open_edu_sim.repository.QuestaoRepository;
import com.example.open_edu_sim.repository.RespostaRepository;
import com.example.open_edu_sim.repository.SimuladoRepository;
import com.example.open_edu_sim.usuario.Usuario;
import com.example.open_edu_sim.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

//@Service
//public class SimuladoService extends GenericService<Simulado> {
//
//    @Autowired
//    private SimuladoRepository simuladoRepository;
//    public List<Simulado> findAllByUsuarioId(Long id) {
//        return simuladoRepository.findAllByUsuarioId(id);
//    }
//
//    public Resposta insertResposta(Long idSimulado, Resposta resposta) {
//        Simulado simulado = simuladoRepository.getById(idSimulado);
//        simulado.getRespostas().add(resposta);
//        update(simulado);
//        return getById(idSimulado).getRespostas().get(getById(idSimulado).getRespostas().size()-1);
//    }
//
//    public Resposta updateResposta(Long idSimulado, Resposta resposta) {
//        Simulado simulado = simuladoRepository.getById(idSimulado);
//        for(int i = 0; i < simulado.getRespostas().size(); i++){
//            if(simulado.getRespostas().get(i).getId() == resposta.getId()){
//                simulado.getRespostas().set(i, resposta);
//            }
//        }
//        update(simulado);
//        return resposta;
//    }
//
//    public Simulado getByIdAndUsuarioId(Long id, Long usuarioId) {
//        return simuladoRepository.getByIdAndUsuarioId(id, usuarioId);
//    }
//
//}

@Service
public class SimuladoService extends GenericService<Simulado>{

    @Autowired
    private SimuladoRepository simuladoRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private RespostaRepository respostaRepository;


    @Autowired
    private UsuarioRepository usuarioRepository;

    public Simulado iniciarSimulado(Usuario usuario, Integer anoEnem, Integer dia) {


        Simulado simulado = new Simulado();
        simulado.setUsuario(usuario);
        simulado.setYear(anoEnem);
        simulado.setDia(dia);
        simulado.setFinalizado(false);
        simulado.setDataInicio(new Timestamp(new Date().getTime()));

        // Salva o simulado
        return simuladoRepository.save(simulado);
    }

    public Simulado getByIdAndUsuarioId(Long id, Long usuarioId) {
        return simuladoRepository.getByIdAndUsuarioId(id, usuarioId);
    }

    public List<Simulado> findAllByUsuarioId(Long id) {
        return simuladoRepository.findAllByUsuarioIdOrderByYearAscDiaAscIdDesc(id);
    }


    public Simulado finalizarSimulado(Long usuarioId, Long simuladoId) {

        Simulado simulado = simuladoRepository.findById(simuladoId)
                .orElseThrow(() -> new RuntimeException("Simulado não encontrado"));

        if (simulado.getFinalizado()) {
            throw new RuntimeException("Este simulado já foi finalizado.");
        }

        int acertosHumanas = 0;
        int acertosLinguagens = 0;
        int acertosCienciasNatureza = 0;
        int acertosMatematica = 0;

        List<Resposta> respostas = respostaRepository.findBySimuladoIdAndUsuarioId(simuladoId, usuarioId);
        String languageFlag = null;

        for (Resposta resposta : respostas) {
            Questao questao = resposta.getQuestao();
            Character alternativaCorreta = questao.getCorrectAlternative();
            Character alternativaSelecionada = resposta.getAlternativaSelecionada();


            if (questao.getDiscipline().equalsIgnoreCase("linguagens") &&
                    (questao.getLanguage() != null && (questao.getLanguage().equalsIgnoreCase("espanhol") ||
                            questao.getLanguage().equalsIgnoreCase("ingles")))) {
                if (languageFlag == null) {
                    languageFlag = questao.getLanguage(); // Define a flag se ainda não estiver setada
                }
            }

            // Verificação de acertos
            if (alternativaCorreta.equals(alternativaSelecionada)) {
                switch (questao.getDiscipline().toLowerCase()) {
                    case "ciencias-humanas":
                        acertosHumanas++;
                        break;
                    case "linguagens":
                        if (languageFlag == null || questao.getLanguage() != null && questao.getLanguage().equalsIgnoreCase(languageFlag)) {
                            acertosLinguagens++;
                        }
                        break;
                    case "ciencias-natureza":
                        acertosCienciasNatureza++;
                        break;
                    case "matematica":
                        acertosMatematica++;
                        break;
                    default:
                        break;
                }
            }
        }

        float pontuacaoHumanas = (acertosHumanas / 45.0f) * 1000;
        float pontuacaoLinguagens = (acertosLinguagens / 45.0f) * 1000;
        float pontuacaoCienciasNatureza = (acertosCienciasNatureza / 45.0f) * 1000;
        float pontuacaoMatematica = (acertosMatematica / 45.0f) * 1000;

        simulado.setPontuacaoHumanas(pontuacaoHumanas);
        simulado.setPontuacaoLinguagens(pontuacaoLinguagens);
        simulado.setPontuacaoCienciasNatureza(pontuacaoCienciasNatureza);
        simulado.setPontuacaoMatematica(pontuacaoMatematica);

        simulado.setFinalizado(true);

        return simuladoRepository.saveAndFlush(simulado);
    }


}
