package api.examen.parcial.u202123541.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal total;
    private String metodo;
    private BigDecimal descuento;
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @ManyToOne
    @JoinColumn(name = "Venta_id")
    private Venta venta;
}
