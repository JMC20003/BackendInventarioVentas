package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.dtos.IngresoPorDiaDTO;
import api.examen.parcial.u202123541.dtos.IngresoPorMesDTO;
import api.examen.parcial.u202123541.dtos.PagoModificacionDTO;
import api.examen.parcial.u202123541.dtos.PagoResponseDTO;
import api.examen.parcial.u202123541.entities.Pago;
import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"https://tiendarjsc.site","http://localhost:4200"})
@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    // Obtener todos los productos
    @GetMapping("/listar")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPagos() {
        List<PagoResponseDTO> pagos = pagoService.getAllPagos();
        return ResponseEntity.ok(pagos);
    }

    // Obtener un pago por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Long id) {
        Pago pago = pagoService.getPagoById(id);
        return ResponseEntity.ok(pago);
    }

    // Obtener un pago por Venta ID
    @GetMapping("/venta-pago/{id}")
    public ResponseEntity<List<Pago>> obtenerPagoVentaPorId(@PathVariable Long id) {
        List<Pago> pago = pagoService.obtenerPagosPorVenta(id);
        return ResponseEntity.ok(pago);
    }

    // Registrar un nuevo pago
    @PostMapping("/registrar")
    public ResponseEntity<Pago> registrarPago(@RequestBody Pago pago) {
        Pago pagoRegistrado = pagoService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoRegistrado);
    }

    //Modificar un pago
    @PutMapping("modificar/{id}")
    public ResponseEntity<PagoResponseDTO> modificarPago(
            @PathVariable Long id,
            @RequestBody PagoModificacionDTO dto) {
        return ResponseEntity.ok(pagoService.modificarPago(id, dto));
    }

    // Eliminar un pago
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    //REPORTES
    @GetMapping("/dias-mas-ingresos")
    public ResponseEntity<List<IngresoPorDiaDTO>> diasMasIngresos() {
        List<IngresoPorDiaDTO> ingresoPorDiaDTOS = pagoService.top5DiasConMasIngresos();
        return ResponseEntity.ok(ingresoPorDiaDTOS);
    }

    @GetMapping("/ingresos-mensuales")
    public ResponseEntity<List<IngresoPorMesDTO>> ingresosMensuales() {
        List<IngresoPorMesDTO> ingresoPorMesDTOS = pagoService.obtenerIngresosPorMes();
        return ResponseEntity.ok(ingresoPorMesDTOS);
    }


}
