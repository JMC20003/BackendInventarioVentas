package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findById(Long id);
}
