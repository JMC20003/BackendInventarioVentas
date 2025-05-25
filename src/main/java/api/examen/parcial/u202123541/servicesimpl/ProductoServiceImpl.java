package api.examen.parcial.u202123541.servicesimpl;

import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.repositories.ProductoRepository;
import api.examen.parcial.u202123541.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(int id) {
        return productoRepository.findById(id).get();
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void deleteById(int id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto producto) {
        //por ahora no vamos a modificar
        return null;
    }
}
