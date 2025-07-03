package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagoModificacionDTO {
    private String metodo;
    private BigDecimal descuento; // Representa un porcentaje, ej. 10.00 para 10%
}