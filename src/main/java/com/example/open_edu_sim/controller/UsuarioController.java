package com.example.open_edu_sim.controller;


import com.example.open_edu_sim.model.PosicaoUsuario;
import com.example.open_edu_sim.model.Ranking;
import com.example.open_edu_sim.model.UsuarioCreateDTO;
import com.example.open_edu_sim.model.UsuarioDTO;
import com.example.open_edu_sim.model.auth.PasswordResetToken;
import com.example.open_edu_sim.repository.TokenRepository;
import com.example.open_edu_sim.service.EmailService;
import com.example.open_edu_sim.service.EmailVerificationService;
import com.example.open_edu_sim.usuario.Usuario;
import com.example.open_edu_sim.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService service;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody @Valid Usuario usuario){
        Usuario user = service.save(usuario);
        StringBuilder builder = new StringBuilder();
        String token = service.createVerificatioEmailToken(user);
        emailService.sendEmail(user.getEmail(), "Verificação de Conta", builder.append("Clique no link para validar seu email: ").append("https://edu-simulator.vercel.app/validate-email?token=").append(token).toString());
        return ResponseEntity.ok("Usuário registrado. Verifique seu e-mail para ativação.");
    }

    @GetMapping("/")
    public ResponseEntity<UsuarioDTO> getUsuarioLogado(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        UsuarioDTO usuarioDTO = UsuarioDTO.convertToUsuarioDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioCreateDTO usuario,
                                             @PathVariable Long id,
                                             Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        if (!usuarioLogado.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Usuario usuarioBanco = service.getById(id);
        if (usuarioBanco == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        usuarioBanco.setNome(usuario.getNome());
        usuarioBanco.setEmail(usuario.getEmail());
        usuarioBanco.setTwoFactorAuthenticationEnabled(usuario.isTwoFactorAuthenticationEnabled());

        service.update(usuarioBanco);

        UsuarioDTO usuarioDTO = UsuarioDTO.convertToUsuarioDTO(usuarioBanco);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/login/{login}")
    public Boolean login(@PathVariable String login){
        Usuario usuarioBanco = service.findByLogin(login);
        return usuarioBanco != null;
    }

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Long id){
        return service.getById(id);
    }


    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        if (emailVerificationService.verifyEmail(token)) {
            return ResponseEntity.ok("E-mail verificado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token de verificação inválido ou expirado.");
        }
    }



    @PostMapping("/password-reset-tk")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        Optional<Usuario> userOpt = Optional.ofNullable(service.findByLogin(email));
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
        StringBuilder builder = new StringBuilder();
        Usuario user = userOpt.get();
        String token = service.createPasswordResetToken(user);
        emailService.sendEmail(user.getEmail(), "Redefinição de senha", builder.append("Clique no link para redefinir sua senha: ").append("https://edu-simulator.vercel.app/reset-password?token=").append(token).toString());

        return ResponseEntity.ok("E-mail de recuperação de senha enviado.");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByToken(token);
        if (resetTokenOpt.isEmpty() || resetTokenOpt.get().isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        Usuario user = resetTokenOpt.get().getUser();
        service.updatePassword(user, newPassword);
        tokenRepository.delete(resetTokenOpt.get());

        return ResponseEntity.ok("Senha alterada com sucesso.");
    }





}