package api.examen.parcial.u202123541.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductoVentaDTO {
    private String nombre;
    private int cantidad;
    private BigDecimal precioUnitario;
}
