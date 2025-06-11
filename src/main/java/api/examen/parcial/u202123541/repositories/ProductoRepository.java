package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findById(Long id);
    List<Producto> findByNombreIgnoreCase(String nombre);
    Optional<Producto> findByNombreIgnoreCaseAndTallaIgnoreCase(String nombre, String talla);
}
