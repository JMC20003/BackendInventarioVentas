package api.examen.parcial.u202123541.servicesimpl;

import api.examen.parcial.u202123541.dtos.*;
import api.examen.parcial.u202123541.entities.Pago;
import api.examen.parcial.u202123541.entities.Venta;
import api.examen.parcial.u202123541.repositories.PagoRepository;
import api.examen.parcial.u202123541.repositories.VentaRepository;
import api.examen.parcial.u202123541.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<PagoResponseDTO> getAllPagos() {
        List<Pago> pagos = pagoRepository.findAll();

        return pagos.stream().map(pago -> {
            List<ProductoVentaDTO> productos = pago.getVenta().getDetallesVenta()
                    .stream()
                    .map(detalle -> {
                        ProductoVentaDTO dto = new ProductoVentaDTO();
                        dto.setNombre(detalle.getProducto().getNombre());
                        dto.setCantidad(detalle.getCantidad());
                        dto.setPrecioUnitario(detalle.getProducto().getPrecio());
                        return dto;
                    })
                    .collect(Collectors.toList());

            PagoResponseDTO dto = new PagoResponseDTO();
            dto.setId(pago.getId());
            dto.setMetodo(pago.getMetodo());
            dto.setTotal(pago.getTotal());
            dto.setDescuento(pago.getDescuento());
            dto.setFechaPago(pago.getFechaPago());
            dto.setFechaVenta(pago.getVenta().getFechaVenta());
            dto.setProductos(productos);


            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Pago> obtenerPagosPorVenta(Long ventaId) {
        return pagoRepository.findAllByVentaId(ventaId);
    }
    @Override
    public Pago getPagoById(Long id) {
        return pagoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pago con id: " + id + " no encontrada"));
    }
    @Override
    public Pago save(Pago pago) {
        Venta venta = ventaRepository.findById(pago.getVenta().getId()).orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Verificar si ya existe un pago para esta venta
        if (pagoRepository.existsByVenta(venta)) {
            throw new RuntimeException("Esta venta ya tiene un pago registrado.");
        }

        BigDecimal subtotal = venta.getSubtotal();
        // Tomar el descuento como porcentaje (ej. 15 = 15%)
        BigDecimal descuentoPorcentaje = pago.getDescuento() != null ? pago.getDescuento() : BigDecimal.ZERO;
        // Calcular el monto del descuento
        BigDecimal descuentoCalculado = subtotal.multiply(descuentoPorcentaje).divide(BigDecimal.valueOf(100));
        // Calcular total final
        BigDecimal total = subtotal.subtract(descuentoCalculado);

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("El descuento no puede dejar el total negativo.");
        }

        Pago newpago = new Pago();
        newpago.setVenta(venta);
        newpago.setFechaPago(LocalDateTime.now());
        newpago.setTotal(total);
        newpago.setMetodo(pago.getMetodo());
        newpago.setDescuento(descuentoPorcentaje);

        return pagoRepository.save(newpago);
    }
    @Override
    @Transactional
    public PagoResponseDTO modificarPago(Long id, PagoModificacionDTO dto) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        // Actualizar método si se envía
        if (dto.getMetodo() != null) {
            pago.setMetodo(dto.getMetodo());
        }

        // Actualizar descuento si se envía
        if (dto.getDescuento() != null) {
            pago.setDescuento(dto.getDescuento());

            // Recalcular el total con descuento
            BigDecimal subtotal = pago.getVenta().getSubtotal();
            BigDecimal descuentoDecimal = dto.getDescuento().divide(BigDecimal.valueOf(100));
            BigDecimal total = subtotal.subtract(subtotal.multiply(descuentoDecimal));
            pago.setTotal(total);
        }

        // Guardar cambios
        Pago pagoActualizado = pagoRepository.save(pago);

        // Construir y retornar el DTO (opcional)
        List<ProductoVentaDTO> productos = pagoActualizado.getVenta().getDetallesVenta()
                .stream()
                .map(detalle -> {
                    ProductoVentaDTO prod = new ProductoVentaDTO();
                    prod.setNombre(detalle.getProducto().getNombre());
                    prod.setCantidad(detalle.getCantidad());
                    prod.setPrecioUnitario(detalle.getProducto().getPrecio());
                    return prod;
                }).collect(Collectors.toList());

        PagoResponseDTO response = new PagoResponseDTO();
        response.setId(pagoActualizado.getId());
        response.setMetodo(pagoActualizado.getMetodo());
        response.setTotal(pagoActualizado.getTotal());
        response.setDescuento(pagoActualizado.getDescuento());
        response.setFechaPago(pagoActualizado.getFechaPago());
        response.setFechaVenta(pagoActualizado.getVenta().getFechaVenta());
        response.setProductos(productos);

        return response;
    }
    @Override
    public void deleteById(Long id) {
        pagoRepository.deleteById(id);
    }


    //REPORTES
    public List<IngresoPorDiaDTO> top5DiasConMasIngresos() {
        List<Object[]> resultados = pagoRepository.obtenerTop5DiasConMasIngresosNativo();

        return resultados.stream()
                .map(obj -> new IngresoPorDiaDTO(
                        ((java.sql.Date) obj[0]).toLocalDate(),
                        (BigDecimal) obj[1]
                )).collect(Collectors.toList());
    }
    public List<IngresoPorMesDTO> obtenerIngresosPorMes(){
        return pagoRepository.obtenerIngresosPorMes();
    }

}
