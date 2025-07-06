package api.examen.parcial.u202123541.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
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
    private String categoria;
    private String descripcion;
    private String imagen;
    private Boolean activo = true;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TallaStock> tallas = new ArrayList<>();

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<DetalleVentaProducto> detallesVenta;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<DetalleCarrito> detallesCarrito;  // Relación con los detalles del carrito
}
