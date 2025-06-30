package api.examen.parcial.u202123541.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalleventaproducto")
public class DetalleVentaProducto {
    @EmbeddedId
    private DetalleVentaProductoId id;

    private int cantidad;
    @ManyToOne
    @MapsId("ventaId")
    @JoinColumn(name = "Venta_id")
    @JsonBackReference
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

    @ManyToOne
    @JoinColumn(name = "Usuario_id")
    @JsonIgnore
    private Usuario usuario;
}
