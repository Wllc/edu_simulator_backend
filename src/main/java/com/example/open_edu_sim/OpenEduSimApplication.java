package com.example.open_edu_sim;

import com.example.open_edu_sim.usuario.Usuario;
import com.example.open_edu_sim.usuario.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class OpenEduSimApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenEduSimApplication.class, args);
	}

	@Autowired
	private UsuarioRepository repository;

//	@PostConstruct
//	public void initAlmocos() {
//
//		List<Usuario> users = Stream.of(
//				new Usuario(1L, "erick", "teste@teste",encoder().encode("1234567"), 0, 0),
//				new Usuario(2L, "emerson", "emersonsilva81240@gmail.com",encoder().encode("1234567"), 0, 0),
//				new Usuario(3L, "maia", "filopemaia2001@gmail.com", encoder().encode("1234567"), 0, 0),
//				new Usuario(4L, "liuz", "luiz.fhs2015@gmail.com", encoder().encode("1234567"), 0, 0)
//
//		).collect(Collectors.toList());
//
//		repository.saveAll(users);
//	}

	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
