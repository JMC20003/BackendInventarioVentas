package api.examen.parcial.u202123541;


import api.examen.parcial.u202123541.entities.Rol;
import api.examen.parcial.u202123541.entities.Usuario;
import api.examen.parcial.u202123541.repositories.RolRepository;
import api.examen.parcial.u202123541.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner mappingDemo(UsuarioRepository usuarioRepo, RolRepository rolRepo, PasswordEncoder encoder) {
		return args -> {
			if (!rolRepo.existsByNombre("ROLE_ADMIN")) {
				Rol rolAdmin = new Rol();
				rolAdmin.setNombre("ROLE_ADMIN");
				rolRepo.save(rolAdmin);
			}

			if (!rolRepo.existsByNombre("ROLE_USER")) {
				Rol rolUser = new Rol();
				rolUser.setNombre("ROLE_USER");
				rolRepo.save(rolUser);
			}

			if (!usuarioRepo.existsByUsername("admin")) {
				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(encoder.encode("admin123"));
				admin.setNombre("Administrador");
				admin.setEmail("admin@demo.com");
				admin.setDireccion("Av. Siempre Viva 123");
				admin.setTelefono("987654321");
				admin.setRoles(Set.of(rolRepo.findByNombre("ROLE_ADMIN")));
				usuarioRepo.save(admin);
			}

			if (!usuarioRepo.existsByUsername("cliente")) {
				Usuario cliente = new Usuario();
				cliente.setUsername("cliente");
				cliente.setPassword(encoder.encode("cliente123"));
				cliente.setNombre("Cliente Prueba");
				cliente.setEmail("cliente@demo.com");
				cliente.setDireccion("Av. Siempre Viva 123");
				cliente.setTelefono("987654321");
				cliente.setRoles(Set.of(rolRepo.findByNombre("ROLE_USER")));
				usuarioRepo.save(cliente);
			}
		};
	}

}
