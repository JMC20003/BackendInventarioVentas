package api.examen.parcial.u202123541.dtos;


import api.examen.parcial.u202123541.entities.Venta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class VentaDetalleDTO {
    private Long id;
    private LocalDateTime fechaVenta;
    private String nombreUsuario;
    private List<DetalleVentaProductoResponseDTO> productosVendidos;

    public VentaDetalleDTO() {
    }

    public VentaDetalleDTO(Venta venta) {
        this.id = venta.getId();
        this.fechaVenta = venta.getFechaVenta();

        if (venta.getUsuario() != null) {
            this.nombreUsuario = venta.getUsuario().getNombre();
        } else {
            this.nombreUsuario = "N/A"; // O algún valor por defecto si el usuario es nulo
        }

        // Aquí mapeas los detalles de la entidad a tu DTO de respuesta para los detalles
        if (venta.getDetallesVenta() != null) {
            this.productosVendidos = venta.getDetallesVenta().stream()
                    .map(DetalleVentaProductoResponseDTO::new) // Asegúrate que este sea el DTO correcto para los detalles de respuesta
                    .collect(Collectors.toList());
        } else {
            this.productosVendidos = List.of(); // Lista vacía si no hay detalles
        }
    }
}
