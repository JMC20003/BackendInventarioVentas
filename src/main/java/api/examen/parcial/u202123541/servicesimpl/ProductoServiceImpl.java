package api.examen.parcial.u202123541.servicesimpl;

import api.examen.parcial.u202123541.dtos.ProductoDTO;
import api.examen.parcial.u202123541.dtos.ProductoRegistroDTO;
import api.examen.parcial.u202123541.dtos.TallaStockDTO;
import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.entities.TallaStock;
import api.examen.parcial.u202123541.repositories.ProductoRepository;
import api.examen.parcial.u202123541.repositories.TallaStockRepository;
import api.examen.parcial.u202123541.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    private TallaStockRepository tallaStockRepository;


    @Override
    public ProductoDTO registrarProducto(ProductoRegistroDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setImagen(dto.getImagen());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        List<TallaStock> listaTallas = dto.getTallas().stream().map(t -> {
            TallaStock ts = new TallaStock();
            ts.setTalla(t.getTalla());
            ts.setStock(t.getStock());
            ts.setProducto(producto);
            return ts;
        }).collect(Collectors.toList());

        producto.setTallas(listaTallas);
        productoRepository.save(producto);
        return mapToDTO(producto);
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToDTO(producto);
    }

    @Override
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream()
                .filter(Producto::getActivo)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoRegistroDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setImagen(dto.getImagen());

        // Eliminar tallas anteriores (orphanRemoval lo hace)
        producto.getTallas().clear();

        List<TallaStock> nuevasTallas = dto.getTallas().stream().map(t -> {
            TallaStock ts = new TallaStock();
            ts.setTalla(t.getTalla());
            ts.setStock(t.getStock());
            ts.setProducto(producto);
            return ts;
        }).collect(Collectors.toList());

        producto.getTallas().addAll(nuevasTallas);
        productoRepository.save(producto);

        return mapToDTO(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        p.setActivo(false);
        //p.setStock(0);
        productoRepository.save(p);
    }

    private ProductoDTO mapToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setDescripcion(producto.getDescripcion());
        dto.setImagen(producto.getImagen());
        dto.setCategoria(producto.getCategoria());

        List<TallaStockDTO> tallas = producto.getTallas().stream()
                .filter(t -> t.getStock() > 0) // solo tallas con stock disponible
                .map(t -> {
                    TallaStockDTO ts = new TallaStockDTO();
                    ts.setTalla(t.getTalla());
                    ts.setStock(t.getStock());
                    return ts;
                })
                .collect(Collectors.toList());

        dto.setTallas(tallas);
        return dto;
    }




    //----------------REPORTES-------------------
    @Override
    public List<Producto> obtenerProductosConStockBajo(int umbral) {
        return productoRepository.findProductosConStockTotalMenorIgual(umbral);
    }

    @Override
    public List<Producto> obtenerProductosConMayorStock() {
        return productoRepository.findProductosOrdenadosPorStockTotalDesc();
    }
}
