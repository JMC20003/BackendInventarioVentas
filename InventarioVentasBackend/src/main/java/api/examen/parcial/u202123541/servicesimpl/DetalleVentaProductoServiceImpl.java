package api.examen.parcial.u202123541.servicesimpl;

import api.examen.parcial.u202123541.entities.DetalleVentaProducto;
import api.examen.parcial.u202123541.entities.DetalleVentaProductoId;
import api.examen.parcial.u202123541.repositories.DetalleVentaProductoRepository;
import api.examen.parcial.u202123541.services.DetalleVentaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaProductoServiceImpl implements DetalleVentaProductoService {


    @Autowired
    private DetalleVentaProductoRepository detalleRepository;

    @Override
    public List<DetalleVentaProducto> getAllDetalles() {
        return detalleRepository.findAll();
    }

    @Override
    public List<DetalleVentaProducto> obtenerDetallesDeVenta(int ventaId) {
        return detalleRepository.findByVentaId(ventaId);
    }

    @Override
    public DetalleVentaProducto getDetalleById (DetalleVentaProductoId id) {
        return detalleRepository.findById(id).orElse(null);
    }

    @Override
    public DetalleVentaProducto save(DetalleVentaProducto detalle) {
        return detalleRepository.save(detalle);
    }

    @Override
    public void deleteById(DetalleVentaProductoId id) {
        detalleRepository.deleteById(id);
    }
}
