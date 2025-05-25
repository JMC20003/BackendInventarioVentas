package api.examen.parcial.u202123541.servicesimpl;

import api.examen.parcial.u202123541.entities.Pago;
import api.examen.parcial.u202123541.repositories.PagoRepository;
import api.examen.parcial.u202123541.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    @Override
    public List<Pago> obtenerPagosPorVenta(int ventaId) {
        return pagoRepository.findByVentaId(ventaId);
    }

    @Override
    public Pago getPagoById(int id) {
        return pagoRepository.findById(id).get();
    }

    @Override
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void deleteById(int id) {
        pagoRepository.deleteById(id);
    }
}
