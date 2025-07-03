package api.examen.parcial.u202123541.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class IngresoPorDiaDTO {
    private LocalDate fecha;
    private BigDecimal total;

    public IngresoPorDiaDTO(LocalDate fecha, BigDecimal total) {
        this.fecha = fecha;
        this.total = total;
    }
}
