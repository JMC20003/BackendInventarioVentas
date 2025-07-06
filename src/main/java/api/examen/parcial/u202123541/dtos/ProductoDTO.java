package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String categoria;
    private String descripcion;
    private String imagen;
    private List<TallaStockDTO> tallas;
}
