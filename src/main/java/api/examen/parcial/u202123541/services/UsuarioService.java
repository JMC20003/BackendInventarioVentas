package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.entities.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByUsername(String username);
    Usuario save(Usuario usuario);
    void deleteById(Long id);
}
