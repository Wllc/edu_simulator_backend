package com.example.open_edu_sim.service;

import com.example.open_edu_sim.model.auth.EmailVerification;
import com.example.open_edu_sim.repository.EmailVerificationRepository;
import com.example.open_edu_sim.usuario.Usuario;
import com.example.open_edu_sim.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final int EXPIRATION_HOURS = 24;

    public void sendVerificationEmail(Usuario usuario) {
        String verificationToken = UUID.randomUUID().toString();

        // Cria e salva a entidade de verificação de e-mail
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setUsuario(usuario);
        emailVerification.setVerificationToken(verificationToken);
        emailVerification.setExpiryDate(LocalDateTime.now().plusHours(EXPIRATION_HOURS));

        emailVerificationRepository.save(emailVerification);

        // Gera o link de verificação e envia o e-mail
        String verificationLink = "http://seusite.com/verify-email?token=" + verificationToken;
        String message = "Clique no link para verificar sua conta: " + verificationLink;

        emailService.sendEmail(usuario.getEmail(), "Verificação de Conta", message);
    }

    public boolean verifyEmail(String token) {
        EmailVerification emailVerification = emailVerificationRepository.findByVerificationToken(token);

        if (emailVerification != null && emailVerification.getExpiryDate().isAfter(LocalDateTime.now())) {
            Usuario usuario = emailVerification.getUsuario();
            usuario.setEmailVerified(true);
            usuarioRepository.save(usuario);
            emailVerificationRepository.delete(emailVerification); // Remove o token após verificação
            return true;
        }

        return false;
    }
}

