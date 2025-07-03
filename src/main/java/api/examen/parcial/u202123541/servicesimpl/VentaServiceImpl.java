package api.examen.parcial.u202123541.servicesimpl;

import api.examen.parcial.u202123541.dtos.*;
import api.examen.parcial.u202123541.entities.*;
import api.examen.parcial.u202123541.repositories.*;
import api.examen.parcial.u202123541.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaProductoRepository detalleRepo;
    @Autowired
    private PagoRepository pagoRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private ProductoRepository productoRepo;

    @Transactional // Es una operación de lectura, no necesita escribir en DB
    public List<VentaDetalleDTO> getAllVentasDetalle() {
        // Obtiene todas las entidades Venta
        List<Venta> ventas = ventaRepository.findAll();

        // Mapea cada entidad Venta a su DTO correspondiente
        return ventas.stream()
                .map(VentaDetalleDTO::new) // Usa el constructor que creamos en VentaDetalleDTO
                .collect(Collectors.toList());
    }
    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }
    @Override
    @Transactional
    public Venta save(RegistroVentaDTO ventaDTO) {
        // 1. Validar que el usuario exista
        Usuario usuario = usuarioRepo.findById(ventaDTO.getUsuarioId())
                .orElseThrow(() -> {
                    return new RuntimeException("Usuario con ID " + ventaDTO.getUsuarioId() + " no encontrado.");
                });

        // 2. Crear la Venta (entidad padre)
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDateTime.now()); // Establece la fecha actual
        venta.setUsuario(usuario);
        venta.setDetallesVenta(new HashSet<>()); // Inicializa la colección de detalles (importante)

        //Calcular el subtotal
        BigDecimal subtotal = ventaDTO.getProductos().stream()
                .map(detalleDTO -> {
                    Producto producto = productoRepo.findById(detalleDTO.getProductoId()).orElseThrow();
                    return producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        venta.setSubtotal(subtotal);
        venta = ventaRepository.save(venta);

        // 3. Procesar cada DetalleVentaProducto (entidades hijas)
        if (ventaDTO.getProductos() == null || ventaDTO.getProductos().isEmpty()) {
            throw new RuntimeException("La venta debe contener al menos un producto.");
        }

        for (DetalleVentaProductoDTO detalleDTO : ventaDTO.getProductos()) {
            // Buscar el producto
            Producto producto = productoRepo.findByIdAndActivoTrue(detalleDTO.getProductoId())
                    .orElseThrow(() -> {
                        return new RuntimeException("Producto con ID " + detalleDTO.getProductoId() + " no encontrado.");
                    });

            // Validar stock
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre() + ". Stock actual: " + producto.getStock() + ", solicitado: " + detalleDTO.getCantidad());
            }

            // Actualizar stock del producto (y guardar el producto con el nuevo stock)
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            // Desactivar si ya no hay stock
            if (producto.getStock() <= 0) {
                producto.setActivo(false);
            }
            productoRepo.save(producto); // Guarda el producto con el stock actualizado

            // Crear el DetalleVentaProducto
            DetalleVentaProducto detalleVenta = new DetalleVentaProducto();
            detalleVenta.setCantidad(detalleDTO.getCantidad());
            detalleVenta.setProducto(producto); // Asigna el producto
            detalleVenta.setVenta(venta);       // Asigna la venta (¡que ya tiene ID!)

            // ESTABLECER LA CLAVE COMPUESTA DetalleVentaProductoId
            detalleVenta.setId(new DetalleVentaProductoId(venta.getId(), producto.getId()));

            // Gracias a CascadeType.ALL en la entidad Venta, estos detalles se guardarán cuando la Venta se guarde.
            venta.getDetallesVenta().add(detalleVenta);
        }

        // 4. Guardar la Venta (esto persistirá también los detalles debido a CascadeType.ALL)
        return ventaRepository.save(venta);
    }

    @Override
    public VentaDetalleDTO obtenerVentaDetallePorId(Long id) {
       Venta venta = ventaRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        return new VentaDetalleDTO(venta);
    }
    //esto esta sobrando creo
    @Override
    public Venta obtenerVentaPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta con ID " + id + " no encontrada"));
        return ventaRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Venta modificarVenta(Long id, RegistroVentaDTO ventaDTO) {
// 1. Buscar la venta existente
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        // 2. Revertir el stock de los productos anteriores
        for (DetalleVentaProducto detalle : venta.getDetallesVenta()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad()); // Devolver stock
            // Reactivar si el stock > 0
            if (producto.getStock() > 0) {
                producto.setActivo(true);
            }
            productoRepo.save(producto);
        }

        BigDecimal subtotal = BigDecimal.ZERO;
        // 3. Eliminar los detalles antiguos
        venta.getDetallesVenta().clear(); // gracias a orphanRemoval = true en la entidad Venta

        // 4. Validar el usuario
        Usuario usuario = usuarioRepo.findById(ventaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + ventaDTO.getUsuarioId() + " no encontrado"));
        venta.setUsuario(usuario);

        // 5. Procesar los nuevos productos
        for (DetalleVentaProductoDTO detalleDTO : ventaDTO.getProductos()) {
            Producto producto = productoRepo.findByIdAndActivoTrue(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + detalleDTO.getProductoId() + " no encontrado"));

            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }


            producto.setStock(producto.getStock() - detalleDTO.getCantidad());

            // Desactivar si ya no queda stock
            if (producto.getStock() <= 0) {
                producto.setActivo(false);
            }

            productoRepo.save(producto);

            DetalleVentaProducto nuevoDetalle = new DetalleVentaProducto();
            nuevoDetalle.setCantidad(detalleDTO.getCantidad());
            nuevoDetalle.setProducto(producto);
            nuevoDetalle.setVenta(venta);
            nuevoDetalle.setId(new DetalleVentaProductoId(venta.getId(), producto.getId()));

            subtotal = subtotal.add(producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad())));

            venta.getDetallesVenta().add(nuevoDetalle);
        }
        venta.setSubtotal(subtotal);
        // 6. Guardar la venta con los nuevos detalles
        return ventaRepository.save(venta);
    }
    @Override
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarVentaDevolucion(Long id) {
        // 1. Buscar la venta
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        // 2. Devolver el stock de los productos
        for (DetalleVentaProducto detalle : venta.getDetallesVenta()) {
            Producto producto = detalle.getProducto();

            // Sumar stock
            producto.setStock(producto.getStock() + detalle.getCantidad());

            // Reactivar si stock > 0
            if (producto.getStock() > 0) {
                producto.setActivo(true);
            }

            productoRepo.save(producto);
        }

        // 3. Eliminar la venta
        ventaRepository.delete(venta);
    }

    //-----------------------Reportes---------------------------
    @Override
    public List<VentaPorMesDTO> obtenerVentasPorMes() {
        return ventaRepository.obtenerVentasPorMes();
    }

    @Override
    public List<ProductoRankingDTO> obtenerProductosMasVendidos() {
        return ventaRepository.obtenerProductosMasVendidos();
    }
    @Override
    public VentaPorMesDTO obtenerMesConMasVentas() {
        return ventaRepository.obtenerMesConMasVentas()
                .stream()
                .findFirst()
                .orElse(null);
    }

}
