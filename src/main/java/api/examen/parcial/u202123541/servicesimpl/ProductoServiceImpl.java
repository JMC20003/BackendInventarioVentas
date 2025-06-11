package api.examen.parcial.u202123541.servicesimpl;

import api.examen.parcial.u202123541.dtos.ProductoCardDTO;
import api.examen.parcial.u202123541.dtos.ProductoRegistroDTO;
import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.repositories.ProductoRepository;
import api.examen.parcial.u202123541.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).get();
    }

//    @Override
//    public Producto save(Producto producto) {
//        return productoRepository.save(producto);
//    }

    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto producto) {
        //por ahora no vamos a modificar
        return null;
    }
    /* =================================================
     *  1) Registrar o actualizar UNA talla
     * ================================================= */
    public ProductoCardDTO registrar(ProductoRegistroDTO dto) {

        // 1.1 ¿Ya existe la combinación nombre + talla?
        Producto producto = productoRepository
                .findByNombreIgnoreCaseAndTallaIgnoreCase(dto.getNombre(), dto.getTalla())
                .orElseGet(Producto::new);

        producto.setNombre(dto.getNombre());
        producto.setTalla(dto.getTalla());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setImagen(dto.getImagen());
        producto.setPrecio(dto.getPrecio());

        // Sumamos stock si ya existía, si no, lo seteamos
        int nuevoStock = dto.getStock() == null ? 0 : dto.getStock();
        producto.setStock(
                producto.getId() == null ? nuevoStock
                        : producto.getStock() + nuevoStock);

        productoRepository.save(producto);

        // 1.2 Construir y devolver card agregada por nombre
        return construirCardPorNombre(dto.getNombre());
    }

    public List<ProductoCardDTO> obtenerCards() {

        return productoRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Producto::getNombre))
                .values()
                .stream()
                .map(this::mapearGrupoACard)
                .toList();
    }

    /** Construye una card para un grupo de productos con EL MISMO nombre */
    private ProductoCardDTO mapearGrupoACard(Collection<Producto> grupo) {
        Producto base = grupo.iterator().next(); // todos comparten nombre y dem ás
        ProductoCardDTO card = new ProductoCardDTO();
        card.setNombre(base.getNombre());
        card.setDescripcion(base.getDescripcion());
        card.setImagen(base.getImagen());
        card.setPrecio(base.getPrecio());
        card.setTallas(
                grupo.stream()
                        .map(Producto::getTalla)
                        .sorted()
                        .distinct()
                        .toList());
        card.setStockTotal(
                grupo.stream()
                        .mapToInt(Producto::getStock)
                        .sum());
        return card;
    }

    /** Card para un nombre concreto (al registrar) */
    private ProductoCardDTO construirCardPorNombre(String nombre) {
        List<Producto> lista = productoRepository.findByNombreIgnoreCase(nombre);
        return mapearGrupoACard(lista);
    }

    @Override
    public ProductoCardDTO getDetalleCardPorId(Long id) {
        // 1. Buscar el producto por ID
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // 2. Obtener todos los productos con el mismo nombre (otras tallas)
        List<Producto> productosConMismoNombre = productoRepository.findByNombreIgnoreCase(producto.getNombre());

        // 3. Mapearlos al DTO
        ProductoCardDTO dto = new ProductoCardDTO();
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setImagen(producto.getImagen());
        dto.setPrecio(producto.getPrecio());

        dto.setTallas(
                productosConMismoNombre.stream()
                        .map(Producto::getTalla)
                        .distinct()
                        .sorted()
                        .toList()
        );

        dto.setStockTotal(
                productosConMismoNombre.stream()
                        .mapToInt(Producto::getStock)
                        .sum()
        );

        return dto;
    }
}
