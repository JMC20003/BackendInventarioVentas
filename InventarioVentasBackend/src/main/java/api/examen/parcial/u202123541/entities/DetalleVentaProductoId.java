package api.examen.parcial.u202123541.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class DetalleVentaProductoId implements Serializable {

    private int ventaId;
    private int productoId;
}
