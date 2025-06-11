package api.examen.parcial.u202123541.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "detalleventaproducto")
public class DetalleVentaProducto {
    @EmbeddedId
    private DetalleVentaProductoId id;

    private int cantidad;
    @ManyToOne
    @MapsId("ventaId")
    @JoinColumn(name = "Venta_id")
    @JsonIgnore
    private Venta venta;

//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "carrito_id")
//    private Carrito carrito;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "Producto_id")
    @JsonIgnore
    private Producto producto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Usuario_id")
    private Usuario usuario;
}
