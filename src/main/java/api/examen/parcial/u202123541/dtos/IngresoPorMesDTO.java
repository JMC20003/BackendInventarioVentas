package api.examen.parcial.u202123541.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IngresoPorMesDTO {
    private String mes;
    private BigDecimal total;

    public IngresoPorMesDTO(String mes, BigDecimal total) {
        this.mes = mes;
        this.total = total;
    }
}
