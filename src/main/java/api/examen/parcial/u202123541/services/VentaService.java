package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.dtos.RegistroVentaDTO;
import api.examen.parcial.u202123541.dtos.VentaDetalleDTO;
import api.examen.parcial.u202123541.entities.Venta;

import java.util.List;

public interface VentaService {
    List<Venta> getAllVentas();
    Venta save(RegistroVentaDTO ventaDTO);
    void deleteById(Long id);
    RegistroVentaDTO registrarVentaCompleta(RegistroVentaDTO registro);
    List<VentaDetalleDTO> getAllVentasDetalle();
    VentaDetalleDTO obtenerVentaDetallePorId(Long id);
    Venta obtenerVentaPorId(Long id);
    // Nuevo método para guardar una venta simple
    Venta saveSimpleVenta(Long usuarioId);
}
