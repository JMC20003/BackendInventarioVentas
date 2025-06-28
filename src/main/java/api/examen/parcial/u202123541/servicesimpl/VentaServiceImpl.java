package api.examen.parcial.u202123541.servicesimpl;

import api.examen.parcial.u202123541.dtos.DetalleVentaProductoDTO;
import api.examen.parcial.u202123541.dtos.PagoDTO;
import api.examen.parcial.u202123541.dtos.RegistroVentaDTO;
import api.examen.parcial.u202123541.dtos.VentaDetalleDTO;
import api.examen.parcial.u202123541.entities.*;
import api.examen.parcial.u202123541.repositories.*;
import api.examen.parcial.u202123541.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

        // *** PASO CRÍTICO: Guardar la Venta primero para que Hibernate le asigne un ID ***
        // Esto es ESENCIAL para las claves compuestas de los detalles
        venta = ventaRepository.save(venta);
        // log.info("Venta guardada inicialmente con ID: {}. Procesando detalles...", venta.getId());

        // 3. Procesar cada DetalleVentaProducto (entidades hijas)
        if (ventaDTO.getProductos() == null || ventaDTO.getProductos().isEmpty()) {
            // log.warn("Advertencia: Intento de registrar venta sin productos.");
            throw new RuntimeException("La venta debe contener al menos un producto.");
        }

        for (DetalleVentaProductoDTO detalleDTO : ventaDTO.getProductos()) {

            // Buscar el producto
            Producto producto = productoRepo.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> {
                        return new RuntimeException("Producto con ID " + detalleDTO.getProductoId() + " no encontrado.");
                    });

            // Validar stock
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre() + ". Stock actual: " + producto.getStock() + ", solicitado: " + detalleDTO.getCantidad());
            }

            // Actualizar stock del producto (y guardar el producto con el nuevo stock)
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepo.save(producto); // Guarda el producto con el stock actualizado

            // Crear el DetalleVentaProducto
            DetalleVentaProducto detalleVenta = new DetalleVentaProducto();
            detalleVenta.setCantidad(detalleDTO.getCantidad());
            detalleVenta.setProducto(producto); // Asigna el producto
            detalleVenta.setVenta(venta);       // Asigna la venta (¡que ya tiene ID!)

            // *** ESTABLECER LA CLAVE COMPUESTA DetalleVentaProductoId ***
            // Asegúrate que DetalleVentaProductoId tenga el constructor y tipos correctos (Long, Integer)
            detalleVenta.setId(new DetalleVentaProductoId(venta.getId(), producto.getId()));


            // Añadir el detalle a la colección de la venta.
            // Gracias a CascadeType.ALL en la entidad Venta, estos detalles se guardarán cuando la Venta se guarde.
            venta.getDetallesVenta().add(detalleVenta);
        }

        // 4. Guardar la Venta (esto persistirá también los detalles debido a CascadeType.ALL)
        // La primera llamada a save ya insertó la Venta. Esta segunda actualizará la Venta
        // y, crucialmente, persistirá los nuevos DetalleVentaProducto en cascada.
        Venta ventaFinal = ventaRepository.save(venta);

        return ventaFinal;
    }
    @Override
    @Transactional // Asegúrate de que tenga transacción
    public Venta saveSimpleVenta(Long usuarioId) {
        // 1. Validar que el usuario exista
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + usuarioId + " no encontrado para venta simple."));

        // 2. Crear la Venta
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDateTime.now());
        venta.setUsuario(usuario);
        venta.setDetallesVenta(new HashSet<>()); // Inicializa, aunque no agregaremos nada

        // 3. Guardar y retornar la Venta
        return ventaRepository.save(venta);
    }

    @Override
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }

    @Override
    public RegistroVentaDTO registrarVentaCompleta(RegistroVentaDTO registro) {
        return null;
    }
    public VentaDetalleDTO obtenerVentaDetallePorId(Long id) {
       return null;
    }
    @Override
    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id).get();
    }
}
