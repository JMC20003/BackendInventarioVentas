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

public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true) // <-- Añade cascade y orphanRemoval
    private Set<DetalleVentaProducto> detallesVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL) // También podrías querer cascada para pagos
    private Set<Pago> pagos;
}
