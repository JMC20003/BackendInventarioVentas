package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.dtos.ProductoRankingDTO;
import api.examen.parcial.u202123541.dtos.RegistroVentaDTO;
import api.examen.parcial.u202123541.dtos.VentaDetalleDTO;
import api.examen.parcial.u202123541.dtos.VentaPorMesDTO;
import api.examen.parcial.u202123541.entities.Venta;

import java.util.List;

public interface VentaService {
    List<Venta> getAllVentas();
    Venta save(RegistroVentaDTO ventaDTO);
    void deleteById(Long id);
    List<VentaDetalleDTO> getAllVentasDetalle();
    VentaDetalleDTO obtenerVentaDetallePorId(Long id);
    Venta obtenerVentaPorId(Long id);
    Venta modificarVenta(Long id, RegistroVentaDTO ventaDTO);
    void eliminarVentaDevolucion(Long id);

    //Reportes
    List<VentaPorMesDTO> obtenerVentasPorMes();
    List<ProductoRankingDTO> obtenerProductosMasVendidos();
    VentaPorMesDTO obtenerMesConMasVentas(); // Solo retorna el primero
}
