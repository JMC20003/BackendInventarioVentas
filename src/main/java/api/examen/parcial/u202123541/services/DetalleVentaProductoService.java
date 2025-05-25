package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.entities.DetalleVentaProducto;
import api.examen.parcial.u202123541.entities.DetalleVentaProductoId;

import java.util.List;

public interface DetalleVentaProductoService {
    List<DetalleVentaProducto> getAllDetalles();
    List<DetalleVentaProducto> obtenerDetallesDeVenta(int ventaId);
    DetalleVentaProducto getDetalleById(DetalleVentaProductoId id);
    DetalleVentaProducto save(DetalleVentaProducto detalle);
    void deleteById(DetalleVentaProductoId id);
}
