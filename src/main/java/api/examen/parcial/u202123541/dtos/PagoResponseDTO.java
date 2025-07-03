package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PagoResponseDTO {
    private Long id;
    private BigDecimal total;
    private String metodo;
    private BigDecimal descuento;
    private LocalDateTime fechaPago;
    private LocalDateTime fechaVenta; // <- nuevo campo
    private List<ProductoVentaDTO> productos;
}
