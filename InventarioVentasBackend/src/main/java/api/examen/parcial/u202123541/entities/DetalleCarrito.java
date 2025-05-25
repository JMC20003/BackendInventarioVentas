package api.examen.parcial.u202123541.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class DetalleCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;  // Relación con Carrito

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;  // Relación con Producto

    private int cantidad;  // Cantidad del producto en el carrito

}
