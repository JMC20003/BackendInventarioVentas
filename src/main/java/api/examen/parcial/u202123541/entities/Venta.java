package api.examen.parcial.u202123541.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;


    @ManyToOne
    @JoinColumn(name = "Usuario_id")
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true) // <-- Añade cascade y orphanRemoval
    @JsonIgnore
    private Set<DetalleVentaProducto> detallesVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private Set<Pago> pagos;
}
