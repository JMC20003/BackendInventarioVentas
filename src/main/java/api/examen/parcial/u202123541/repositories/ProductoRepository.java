package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findById(Long id);
}
