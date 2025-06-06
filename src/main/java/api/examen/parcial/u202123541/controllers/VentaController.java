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

@CrossOrigin(origins = {"https://proyecto5-e5bb9.web.app","http://localhost:4200"})
@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaServicio;

    // Obtener todas las ventas
    @GetMapping("/listar")
    public ResponseEntity<List<VentaDetalleDTO>> obtenerVentas() {
        List<VentaDetalleDTO> ventasDetalle = ventaServicio.getAllVentasDetalle(); // Llama al nuevo método
        return ResponseEntity.ok(ventasDetalle);
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

    @PostMapping("/registrar")
    public ResponseEntity<VentaDetalleDTO> registrarVenta(@RequestBody RegistroVentaDTO ventaDTO) {
        try {
            Venta nuevaVenta = ventaServicio.save(ventaDTO);
            VentaDetalleDTO responseDTO = new VentaDetalleDTO(nuevaVenta);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // ¡¡¡CAMBIO AQUÍ!!!
            System.err.println("**************************************************");
            System.err.println("¡¡¡ERROR CAPTURADO EN VentaController!!!: " + e.getMessage());
            e.printStackTrace(); // Esto imprimirá la traza de pila completa
            System.err.println("**************************************************");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Nuevo endpoint para probar el guardado simple
    @GetMapping("/registrar/simple/{usuarioId}")
    public ResponseEntity<VentaDetalleDTO> registrarVentaSimple(@PathVariable Long usuarioId) {
        try {
            Venta nuevaVenta = ventaServicio.saveSimpleVenta(usuarioId);
            // Mapea la entidad Venta a tu DTO de respuesta
            VentaDetalleDTO responseDTO = new VentaDetalleDTO(nuevaVenta);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.err.println("Error al registrar venta simple: " + e.getMessage()); // Log simple si no hay logger
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ListarDTO/{id}")
    public ResponseEntity<VentaDetalleDTO> obtenerVentaDetallePorId(@PathVariable Long id) {
        VentaDetalleDTO dto = ventaServicio.obtenerVentaDetallePorId(id);
        return ResponseEntity.ok(dto);
    }
}
