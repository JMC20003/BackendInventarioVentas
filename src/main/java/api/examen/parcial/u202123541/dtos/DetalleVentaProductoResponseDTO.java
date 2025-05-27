package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetalleVentaProductoResponseDTO {
    private Long productoId;
    private String nombreProducto;
    private int cantidad;
    private BigDecimal precioUnitario;

    public DetalleVentaProductoResponseDTO(api.examen.parcial.u202123541.entities.DetalleVentaProducto detalle) {
        // Este constructor es el que se invoca en el .map()
        if (detalle.getProducto() != null) {
            this.productoId = detalle.getProducto().getId().longValue();
            this.nombreProducto = detalle.getProducto().getNombre();
            this.precioUnitario = detalle.getProducto().getPrecio();
        }
        this.cantidad = detalle.getCantidad();
    }
}
