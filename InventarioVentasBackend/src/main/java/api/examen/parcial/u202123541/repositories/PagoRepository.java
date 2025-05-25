package api.examen.parcial.u202123541.repositories;

import api.examen.parcial.u202123541.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByVentaId (int id);
}
