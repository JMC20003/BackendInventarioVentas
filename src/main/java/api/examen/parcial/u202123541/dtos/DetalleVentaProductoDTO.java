package api.examen.parcial.u202123541.dtos;

import api.examen.parcial.u202123541.entities.DetalleVentaProducto;
import lombok.Data;

@Data
public class DetalleVentaProductoDTO {
    private Long productoId;
    private String talla; // 👈 talla vendida
    private int cantidad;
}
