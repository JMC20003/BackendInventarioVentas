package api.examen.parcial.u202123541.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoRankingDTO {
    private String nombreProducto;
    private Long cantidadVendida;

    public ProductoRankingDTO(String nombreProducto, Long cantidadVendida) {
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
    }
}
