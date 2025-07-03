package api.examen.parcial.u202123541.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VentaPorMesDTO {
    private String mes;
    private Long cantidadVentas;
    private BigDecimal totalVendido;

    public VentaPorMesDTO(String mes, Long cantidadVentas, BigDecimal totalVendido) {
        this.mes = mes;
        this.cantidadVentas = cantidadVentas;
        this.totalVendido = totalVendido;
    }
}
