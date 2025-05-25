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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta save(Venta venta) {
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
