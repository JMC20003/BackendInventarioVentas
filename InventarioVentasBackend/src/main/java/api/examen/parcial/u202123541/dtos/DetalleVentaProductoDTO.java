package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DetalleVentaProductoDTO {
    private int productoId;  // Puede ser necesario para referenciar el producto
    private String nombreProducto;
    private int cantidad;
    private double precio;
}
