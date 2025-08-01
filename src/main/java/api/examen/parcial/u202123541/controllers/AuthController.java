package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.dtos.AuthRequest;
import api.examen.parcial.u202123541.dtos.AuthResponse;
import api.examen.parcial.u202123541.dtos.RegisterRequest;
import api.examen.parcial.u202123541.entities.Rol;
import api.examen.parcial.u202123541.entities.Usuario;
import api.examen.parcial.u202123541.repositories.RolRepository;
import api.examen.parcial.u202123541.repositories.UsuarioRepository;
import api.examen.parcial.u202123541.security.JwtUtilService;
import api.examen.parcial.u202123541.servicesimpl.UserDetailsImpl;
import api.examen.parcial.u202123541.servicesimpl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"https://tiendarjsc.site","http://localhost:4200","https://backoffice.tiendarjsc.site"})
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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            // Aquí casteamos al tipo personalizado que creamos antes
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Generar token
            String token = jwtUtil.generateToken(userDetails);

            // Obtener roles
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Construir la respuesta
            AuthResponse response = new AuthResponse(
                    token,
                    userDetails.getId(),         // ID del usuario
                    userDetails.getUsername(),   // Email
                    roles                         // Lista de roles
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(null); // o usa un mensaje con otro DTO
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
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
