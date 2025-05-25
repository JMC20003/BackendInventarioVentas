package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.entities.DetalleVentaProducto;
import api.examen.parcial.u202123541.entities.DetalleVentaProductoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetalleVentaProductoRepository extends JpaRepository<DetalleVentaProducto, Integer> {
    Optional<DetalleVentaProducto> findById(DetalleVentaProductoId id);
    List<DetalleVentaProducto> findByVentaId(int ventaId);
    void deleteById(DetalleVentaProductoId id);
}
