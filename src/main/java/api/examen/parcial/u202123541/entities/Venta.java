package api.examen.parcial.u202123541.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "Venta")
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

    @JsonIgnore
    @OneToMany(mappedBy = "venta")
    private Set<DetalleVentaProducto> detallesVenta;

    @JsonIgnore
    @OneToMany(mappedBy = "venta")
    private Set<Pago> pagos;
}
