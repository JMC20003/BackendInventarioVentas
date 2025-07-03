package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.dtos.ProductoRankingDTO;
import api.examen.parcial.u202123541.dtos.VentaPorMesDTO;
import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findById(Long id);

    //----------Reportes----------------
    @Query("SELECT NEW api.examen.parcial.u202123541.dtos.VentaPorMesDTO(FUNCTION('TO_CHAR', v.fechaVenta, 'YYYY-MM'), COUNT(v), SUM(v.subtotal)) " +
            "FROM Venta v " +
            "GROUP BY FUNCTION('TO_CHAR', v.fechaVenta, 'YYYY-MM') " +
            "ORDER BY FUNCTION('TO_CHAR', v.fechaVenta, 'YYYY-MM')")
    List<VentaPorMesDTO> obtenerVentasPorMes();

    @Query("SELECT NEW api.examen.parcial.u202123541.dtos.ProductoRankingDTO(p.nombre, SUM(d.cantidad)) " +
            "FROM DetalleVentaProducto d " +
            "JOIN d.producto p " +
            "GROUP BY p.nombre " +
            "ORDER BY SUM(d.cantidad) DESC")
    List<ProductoRankingDTO> obtenerProductosMasVendidos();

    @Query("SELECT NEW api.examen.parcial.u202123541.dtos.VentaPorMesDTO(FUNCTION('TO_CHAR', v.fechaVenta, 'YYYY-MM'), COUNT(v), SUM(v.subtotal)) " +
            "FROM Venta v " +
            "GROUP BY FUNCTION('TO_CHAR', v.fechaVenta, 'YYYY-MM') " +
            "ORDER BY SUM(v.subtotal) DESC")
    List<VentaPorMesDTO> obtenerMesConMasVentas(); // Solo tomas el primero
}
