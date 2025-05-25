package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.entities.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> getAllProductos();
    Producto getProductoById(int id);
    Producto save(Producto producto);
    void deleteById(int id);
    Producto actualizarProducto(Long id, Producto producto);
}
