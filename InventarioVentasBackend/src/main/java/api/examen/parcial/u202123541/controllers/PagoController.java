package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.entities.Pago;
import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    // Obtener todos los productos
    @GetMapping("/listar")
    public ResponseEntity<List<Pago>> obtenerPagos() {
        List<Pago> pagos = pagoService.getAllPagos();
        return ResponseEntity.ok(pagos);
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable int id) {
        Pago pago = pagoService.getPagoById(id);
        return ResponseEntity.ok(pago);
    }

    // Registrar un nuevo producto
    @PostMapping("/registrar")
    public ResponseEntity<Pago> registrarPago(@RequestBody Pago pago) {
        Pago pagoRegistrado = pagoService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoRegistrado);
    }


    // Eliminar un producto
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable int id) {
        pagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
