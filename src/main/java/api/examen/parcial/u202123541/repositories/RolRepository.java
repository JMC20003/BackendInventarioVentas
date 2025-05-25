package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);

    boolean existsByNombre(String roleAdmin);
}
