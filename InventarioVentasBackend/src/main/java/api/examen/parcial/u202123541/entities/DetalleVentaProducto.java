package api.examen.parcial.u202123541.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DetalleVentaProducto")
public class DetalleVentaProducto {
    @EmbeddedId
    private DetalleVentaProductoId id;

    @ManyToOne
    @MapsId("ventaId")
    @JoinColumn(name = "Venta_id")
    private Venta venta;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JsonIgnore
    @MapsId("productoId")
    @JoinColumn(name = "Producto_id")
    private Producto producto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Usuario_id")
    private Usuario usuario;
}
