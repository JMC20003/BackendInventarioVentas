package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.dtos.RegistroVentaDTO;
import api.examen.parcial.u202123541.dtos.VentaDetalleDTO;
import api.examen.parcial.u202123541.entities.Venta;
import api.examen.parcial.u202123541.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://backendinventarioventas.onrender.com")
@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaServicio;

    // Obtener todas las ventas
    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> obtenerVentas() {
        List<Venta> ventas = ventaServicio.getAllVentas();
        return ResponseEntity.ok(ventas);
    }

    // Obtener una venta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        Venta venta = ventaServicio.obtenerVentaPorId(id);
        return ResponseEntity.ok(venta);
    }

    // Eliminar un producto
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaServicio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Crear una nueva venta
    @PostMapping("/registrar")
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta ventaCreada = ventaServicio.save(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaCreada);
    }

    // Crear una nueva venta con DTO
    @PostMapping("/registrarDTO")
    public ResponseEntity<RegistroVentaDTO> registrarVenta(@RequestBody RegistroVentaDTO dto) {
        // Llamamos al servicio para registrar la venta completa (venta, detalles y pago)
        RegistroVentaDTO resultado = ventaServicio.registrarVentaCompleta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @GetMapping("/ListarDTO/{id}")
    public ResponseEntity<VentaDetalleDTO> obtenerVentaDetallePorId(@PathVariable Long id) {
        VentaDetalleDTO dto = ventaServicio.obtenerVentaDetallePorId(id);
        return ResponseEntity.ok(dto);
    }
}
