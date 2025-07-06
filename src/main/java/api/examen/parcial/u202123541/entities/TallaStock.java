package api.examen.parcial.u202123541.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Data
public class TallaStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String talla;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
