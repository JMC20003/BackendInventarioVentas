package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.dtos.ProductoCardDTO;
import api.examen.parcial.u202123541.dtos.ProductoRegistroDTO;
import api.examen.parcial.u202123541.entities.Producto;

import java.util.Collection;
import java.util.List;

public interface ProductoService {
    List<Producto> getAllProductos();
    Producto getProductoById(Long id);
    void deleteById(Long id);
    ProductoCardDTO actualizarProducto(Long id, ProductoRegistroDTO producto);
    public ProductoCardDTO registrar(ProductoRegistroDTO dto);
    public List<ProductoCardDTO> obtenerCards();
    public ProductoCardDTO getDetalleCardPorId(Long id);

    //REPORTES
    List<Producto> obtenerProductosConStockBajo(int umbral);
    List<Producto> obtenerProductosConMayorStock();
}
