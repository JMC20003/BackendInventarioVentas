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

    @Transactional
    public Venta save(RegistroVentaDTO ventaDTO) {
        // 1. Validar que el usuario exista
        Usuario usuario = usuarioRepo.findById(ventaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Crear la Venta
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDateTime.now());
        venta.setUsuario(usuario);
        venta.setDetallesVenta(new HashSet<>()); // Inicializar

        // --- ¡NUEVO PASO CRÍTICO! ---
        // Guarda la venta inicial para que Hibernate le asigne un ID
        venta = ventaRepository.save(venta); // Ahora 'venta' tiene un ID

        // 3. Procesar cada DetalleVentaProducto
        for (DetalleVentaProductoDTO detalleDTO : ventaDTO.getProductos()) {
            Producto producto = productoRepo.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + detalleDTO.getProductoId() + " no encontrado"));

            // Validar stock
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Actualizar stock del producto
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepo.save(producto);

            // Crear el DetalleVentaProducto
            DetalleVentaProducto detalleVenta = new DetalleVentaProducto();
            detalleVenta.setCantidad(detalleDTO.getCantidad());
            detalleVenta.setProducto(producto);
            detalleVenta.setVenta(venta); // Vincular al objeto Venta (ya tiene ID)

            // Establecer la PK compuesta usando el ID de la venta ya persistida
            detalleVenta.setId(new DetalleVentaProductoId(venta.getId(), producto.getId()));

            venta.getDetallesVenta().add(detalleVenta);
        }

        // 4. Los detalles se guardarán en cascada si CascadeType.ALL está configurado
        // Como ya guardamos la venta, esta segunda llamada a save puede ser redundante
        // si CascadeType.ALL es efectivo y los detalles ya están asociados.
        // Pero es seguro dejarlo para asegurar la persistencia de los detalles.
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
