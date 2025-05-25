package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.dtos.AuthRequest;
import api.examen.parcial.u202123541.dtos.AuthResponse;
import api.examen.parcial.u202123541.dtos.RegisterRequest;
import api.examen.parcial.u202123541.entities.Rol;
import api.examen.parcial.u202123541.entities.Usuario;
import api.examen.parcial.u202123541.repositories.RolRepository;
import api.examen.parcial.u202123541.repositories.UsuarioRepository;
import api.examen.parcial.u202123541.security.JwtUtilService;
import api.examen.parcial.u202123541.servicesimpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userDetailsService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(registerRequest.getUsername());
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        usuario.setNombre(registerRequest.getNombre());
        usuario.setDireccion(registerRequest.getDireccion());
        usuario.setTelefono(registerRequest.getTelefono());
        usuario.setEmail(registerRequest.getEmail());

        // Rol por defecto: ROLE_USER (debes tenerlo creado en la tabla Rol)
        Rol rol = rolRepository.findByNombre("ROLE_USER");
        usuario.setRoles(Set.of(rol));

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado correctamente."));
    }
}
