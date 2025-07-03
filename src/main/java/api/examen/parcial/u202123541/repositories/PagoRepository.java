package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.dtos.IngresoPorDiaDTO;
import api.examen.parcial.u202123541.dtos.IngresoPorMesDTO;
import api.examen.parcial.u202123541.entities.Pago;
import api.examen.parcial.u202123541.entities.Venta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    Pago findByVentaId (Long id);
    List<Pago> findAllByVentaId (Long id);
    boolean existsByVenta(Venta venta);

    //REPORTES
    @Query(value = "SELECT DATE(fecha_pago) AS fecha, SUM(total) AS total " +
            "FROM pago " +
            "GROUP BY DATE(fecha_pago) " +
            "ORDER BY total DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> obtenerTop5DiasConMasIngresosNativo();
    @Query("SELECT NEW api.examen.parcial.u202123541.dtos.IngresoPorMesDTO(FUNCTION('TO_CHAR', p.fechaPago, 'YYYY-MM'), SUM(p.total)) " +
            "FROM Pago p " +
            "GROUP BY FUNCTION('TO_CHAR', p.fechaPago, 'YYYY-MM') " +
            "ORDER BY FUNCTION('TO_CHAR', p.fechaPago, 'YYYY-MM')")
    List<IngresoPorMesDTO> obtenerIngresosPorMes();

}
