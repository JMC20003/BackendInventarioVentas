package api.examen.parcial.u202123541.entities;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode // ESENCIAL
@NoArgsConstructor // ESENCIAL para JPA
public class DetalleVentaProductoId implements Serializable {

    private Long ventaId;
    private Integer productoId;

    public DetalleVentaProductoId(Long ventaId, Integer productoId) {
        this.ventaId = ventaId;
        this.productoId = productoId;
    }
}
