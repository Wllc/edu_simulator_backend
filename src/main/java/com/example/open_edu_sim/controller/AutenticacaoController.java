package com.example.open_edu_sim.controller;


import com.example.open_edu_sim.security.DadosTokenJWT;
import com.example.open_edu_sim.security.TokenService;
import com.example.open_edu_sim.service.TwoFactorAuthService;
import com.example.open_edu_sim.usuario.DadosAutenticacao;
import com.example.open_edu_sim.usuario.Usuario;
import com.example.open_edu_sim.usuario.UsuarioRepository;
import com.example.open_edu_sim.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwoFactorAuthService twoFactorAuthService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var authentication = manager.authenticate(authenticationToken);
            var usuario = (Usuario) authentication.getPrincipal();

            if (!usuario.isEmailVerified()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("E-mail não validado, cheque sua caixa de entrada!");
            }

            if (usuario.isTwoFactorAuthenticationEnabled()) {
                twoFactorAuthService.generateAndSend2FACode(usuario);
                return ResponseEntity.accepted().body("Código de autenticação enviado para o e-mail.");
            } else {
                String tokenJWT = tokenService.gerarToken(usuario);
                return ResponseEntity.ok(new DadosTokenJWT(tokenJWT, usuario.getNome()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro no login, já possui uma conta?");
        }
    }

    @PostMapping("/validate-2fa")
    public ResponseEntity<?> validateTwoFactor(@RequestParam String username, @RequestParam String code) {
        try {
            Optional<Usuario> userOpt = Optional.ofNullable(usuarioService.findByLogin(username));
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
            Usuario usuario = userOpt.get();
            if (twoFactorAuthService.validate2FACode(usuario, code)) {
                String tokenJWT = tokenService.gerarToken(usuario);
                return ResponseEntity.ok(new DadosTokenJWT(tokenJWT, usuario.getNome()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código 2FA inválido ou expirado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao validar 2FA.");
        }
    }

}
