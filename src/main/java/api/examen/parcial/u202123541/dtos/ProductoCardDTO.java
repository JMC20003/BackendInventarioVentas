package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductoCardDTO {
    private String nombre;
    private String descripcion;
    private String imagen;
    private BigDecimal precio;   // asumimos mismo precio
    private List<String> tallas; // únicas
    private Integer stockTotal;  // suma de stock
}
