package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductoRegistroDTO {
    private String nombre;
    private BigDecimal precio;
    private String categoria;
    private String descripcion;
    private String imagen;

    private List<TallaStockDTO> tallas = new ArrayList<>();
    //Para eliminar un producto que se registro en una venta
    private Boolean activo = true;
}