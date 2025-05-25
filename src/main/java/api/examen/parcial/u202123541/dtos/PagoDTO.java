package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PagoDTO {
    private double monto;
    private String metodo;
    private LocalDateTime fechaPago;
}
