package com.example.open_edu_sim.service;

import com.example.open_edu_sim.model.auth.TwoFactorAuthentication;
import com.example.open_edu_sim.repository.TwoFactorAuthenticationRepository;
import com.example.open_edu_sim.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TwoFactorAuthService {

    @Autowired
    private TwoFactorAuthenticationRepository twoFactorAuthRepository;

    @Autowired
    private EmailService emailService;

    private static final int CODE_EXPIRATION_MINUTES = 120;

    public void generateAndSend2FACode(Usuario usuario) {
        TwoFactorAuthentication twoFactorAuthentication = twoFactorAuthRepository.findByUsuario(usuario);
        if(twoFactorAuthentication != null) twoFactorAuthRepository.delete(twoFactorAuthentication);
        String code = generateRandomCode();

        TwoFactorAuthentication twoFactorAuth = new TwoFactorAuthentication();
        twoFactorAuth.setUsuario(usuario);
        twoFactorAuth.setCode(code);
        twoFactorAuth.setExpiryDate(LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES));

        twoFactorAuthRepository.save(twoFactorAuth);

        String message = "Seu código de autenticação de dois fatores é: " + code;
        emailService.sendEmail(usuario.getEmail(), "Código de Autenticação de Dois Fatores", message);
    }

    public boolean validate2FACode(Usuario usuario, String code) {
        TwoFactorAuthentication twoFactorAuth = twoFactorAuthRepository.findByUsuario(usuario);

        boolean valid = twoFactorAuth != null &&
                twoFactorAuth.getCode().equals(code) &&
                twoFactorAuth.getExpiryDate().isAfter(LocalDateTime.now());
        if(valid) twoFactorAuthRepository.delete(twoFactorAuth);
        return valid;
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
