package com.example.open_edu_sim.usuario;

import com.example.open_edu_sim.model.auth.EmailVerification;
import com.example.open_edu_sim.model.auth.PasswordResetToken;
import com.example.open_edu_sim.repository.EmailVerificationRepository;
import com.example.open_edu_sim.repository.TokenRepository;
import com.example.open_edu_sim.service.EmailService;
import com.example.open_edu_sim.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    private static final int EXPIRATION_HOURS = 24;

    public Usuario save(Usuario usuario){
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario.setEmailVerified(false);
        return repository.save(usuario);

    }

    public void update(Usuario usuario) {
        repository.saveAndFlush(usuario);
    }

    public Usuario findByLogin(String login){
        return (Usuario) repository.findByEmail(login);
    }

    public Usuario getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Usuario> buscarUsuariosOrdenadosPorPontuacaoTotal(Integer pagina, Integer tamanhoPagina) {
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAllByOrderByPontuacaoTotalDesc(pageable);
    }

//    public Long encontrarPosicaoUsuarioPorLogin(String login) {
//        return repository.countByPontuacaoTotalGreaterThanEqual(repository.findPontuacaoTotalPorLogin(login));
//    }

    public String createPasswordResetToken(Usuario user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        tokenRepository.save(passwordResetToken);
        return token;
    }

    public String createVerificatioEmailToken(Usuario user) {
        String verificationToken = UUID.randomUUID().toString();

        // Cria e salva a entidade de verificação de e-mail
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setUsuario(user);
        emailVerification.setVerificationToken(verificationToken);
        emailVerification.setExpiryDate(LocalDateTime.now().plusHours(EXPIRATION_HOURS));

        emailVerificationRepository.save(emailVerification);

        return verificationToken;
    }

    public void updatePassword(Usuario user, String newPassword) {
        user.setSenha(encoder.encode(newPassword));
        repository.save(user);
    }
}
