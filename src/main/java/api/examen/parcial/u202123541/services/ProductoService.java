package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.dtos.ProductoDTO;
import api.examen.parcial.u202123541.dtos.ProductoRegistroDTO;
import api.examen.parcial.u202123541.entities.Producto;

import java.util.List;

public interface ProductoService {
    ProductoDTO registrarProducto(ProductoRegistroDTO dto);
    ProductoDTO obtenerProductoPorId(Long id);
    List<ProductoDTO> listarProductos();
    ProductoDTO actualizarProducto(Long id, ProductoRegistroDTO dto);
    void eliminarProducto(Long id); // baja lógica

    //REPORTES
    List<Producto> obtenerProductosConStockBajo(int umbral);
    List<Producto> obtenerProductosConMayorStock();
}
