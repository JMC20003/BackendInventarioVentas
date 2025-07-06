package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findById(Long id);
    //List<Producto> findByNombreIgnoreCase(String nombre);
    //Optional<Producto> findByNombreIgnoreCaseAndTallaIgnoreCase(String nombre, String talla);

    Optional<Producto> findByIdAndActivoTrue(Long id);

    //REPORTES
    // Productos con stock menor o igual a un umbral
    @Query("SELECT p FROM Producto p " +
            "JOIN p.tallas t " +
            "WHERE p.activo = true " +
            "GROUP BY p " +
            "HAVING SUM(t.stock) <= :umbral")
    List<Producto> findProductosConStockTotalMenorIgual(@Param("umbral") int umbral);

    // Productos ordenados por mayor stock (activos)
    @Query("SELECT p FROM Producto p " +
            "JOIN p.tallas t " +
            "WHERE p.activo = true " +
            "GROUP BY p " +
            "ORDER BY SUM(t.stock) DESC")
    List<Producto> findProductosOrdenadosPorStockTotalDesc();
}
