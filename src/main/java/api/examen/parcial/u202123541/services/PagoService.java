package api.examen.parcial.u202123541.services;

import api.examen.parcial.u202123541.dtos.IngresoPorDiaDTO;
import api.examen.parcial.u202123541.dtos.IngresoPorMesDTO;
import api.examen.parcial.u202123541.dtos.PagoModificacionDTO;
import api.examen.parcial.u202123541.dtos.PagoResponseDTO;
import api.examen.parcial.u202123541.entities.Pago;

import java.util.List;

public interface PagoService {
    List<PagoResponseDTO> getAllPagos();
    List<Pago> obtenerPagosPorVenta(Long ventaId);
    Pago getPagoById(Long id);
    Pago save(Pago pago);
    PagoResponseDTO modificarPago(Long id, PagoModificacionDTO dto);
    void deleteById(Long id);


    //REPORTES
    List<IngresoPorDiaDTO> top5DiasConMasIngresos();
    List<IngresoPorMesDTO> obtenerIngresosPorMes();
}
