package api.examen.parcial.u202123541.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Pago")
public class Pago {
    @Id
    private int id;
    private double monto;
    private String metodo;

    @Column(name = "fecha_pago")
    private Date fechaPago;

    @ManyToOne
    @JoinColumn(name = "Venta_id")
    private Venta venta;
}
