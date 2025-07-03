package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findById(Long id);
    List<Producto> findByNombreIgnoreCase(String nombre);
    Optional<Producto> findByNombreIgnoreCaseAndTallaIgnoreCase(String nombre, String talla);
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.stock > 0")
    List<Producto> obtenerProductosDisponibles();

    Optional<Producto> findByIdAndActivoTrue(Long id);

    //REPORTES
    // Productos con stock menor o igual a un umbral
    List<Producto> findByStockLessThanEqualAndActivoTrue(int umbral);

    // Productos ordenados por mayor stock (activos)
    List<Producto> findByActivoTrueOrderByStockDesc();
}
