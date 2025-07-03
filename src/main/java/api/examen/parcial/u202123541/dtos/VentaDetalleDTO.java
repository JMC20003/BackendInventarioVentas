package api.examen.parcial.u202123541.dtos;


import api.examen.parcial.u202123541.entities.Usuario;
import api.examen.parcial.u202123541.entities.Venta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class VentaDetalleDTO {
    private Long id;
    private LocalDateTime fechaVenta;
    private String nombreUsuario;
    private BigDecimal subTotal;
    private List<DetalleVentaProductoResponseDTO> productosVendidos;

    public VentaDetalleDTO() {
    }

    public VentaDetalleDTO(Venta venta) {
        this.id = venta.getId();
        this.fechaVenta = venta.getFechaVenta();
        this.subTotal=venta.getSubtotal();
        this.nombreUsuario = Optional.ofNullable(venta.getUsuario())
                .map(Usuario::getNombre)
                .orElse("N/A");

        this.productosVendidos = Optional.ofNullable(venta.getDetallesVenta())
                .orElse(Set.of()) // o HashSet::new si prefieres
                .stream()
                .map(DetalleVentaProductoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
