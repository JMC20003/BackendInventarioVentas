package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RegistroVentaDTO {
    private Long usuarioId;
    private List<VentaDetalleDTO> ventaDetalles;  // Cambio nombre de 'ventas' a 'ventaDetalles'
    private PagoDTO pago;
}
