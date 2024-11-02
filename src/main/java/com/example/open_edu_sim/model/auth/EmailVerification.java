package com.example.open_edu_sim.model.auth;

import com.example.open_edu_sim.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, unique = true)
    private String verificationToken;

    private LocalDateTime expiryDate;

    public boolean isTokenExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }
}
