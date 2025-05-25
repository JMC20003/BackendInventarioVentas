package api.examen.parcial.u202123541.dtos;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaDetalleDTO {
    private Long id;  // Cambié 'id' para hacerla consistente con las convenciones de tipos de datos
    private LocalDateTime fechaVenta;
    private String usuarioNombre;
    private List<DetalleVentaProductoDTO> detalles;
    private List<PagoDTO> pagos;
}
