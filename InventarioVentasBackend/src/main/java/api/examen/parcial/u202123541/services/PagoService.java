package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.entities.Pago;

import java.util.List;

public interface PagoService {
    List<Pago> getAllPagos();
    List<Pago> obtenerPagosPorVenta(int ventaId);
    Pago getPagoById(int id);
    Pago save(Pago pago);
    void deleteById(int id);
}
