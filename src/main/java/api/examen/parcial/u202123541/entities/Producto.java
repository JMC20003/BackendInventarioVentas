package api.examen.parcial.u202123541.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private String categoria;
    private String descripcion;
    private String imagen;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<DetalleVentaProducto> detallesVenta;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<DetalleCarrito> detallesCarrito;  // Relación con los detalles del carrito
}
