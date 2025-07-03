package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.dtos.ProductoRankingDTO;
import api.examen.parcial.u202123541.dtos.RegistroVentaDTO;
import api.examen.parcial.u202123541.dtos.VentaDetalleDTO;
import api.examen.parcial.u202123541.dtos.VentaPorMesDTO;
import api.examen.parcial.u202123541.entities.Venta;
import api.examen.parcial.u202123541.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    //@GetMapping("/{id}")
    //public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
    //    Venta venta = ventaServicio.obtenerVentaPorId(id);
    //    return ResponseEntity.ok(venta);
    //}
    @GetMapping("/{id}")
    public ResponseEntity<VentaDetalleDTO> obtenerVentaDetallePorId(@PathVariable Long id) {
        VentaDetalleDTO dto = ventaServicio.obtenerVentaDetallePorId(id);
        return ResponseEntity.ok(dto);
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
    @PutMapping("/modificar/{id}")
    public ResponseEntity<VentaDetalleDTO> modificarVenta(@PathVariable Long id, @RequestBody RegistroVentaDTO ventaDTO) {
        try {
            Venta nuevaVenta = ventaServicio.modificarVenta(id, ventaDTO);
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
    //ELIMINAR EN CASO DE DEVOLUCION DEL PRODUCTO
    @DeleteMapping("/devolucion/{id}")
    public ResponseEntity<Void> eliminarVentaDevolucion(@PathVariable Long id) {
        ventaServicio.eliminarVentaDevolucion(id);
        return ResponseEntity.noContent().build();
    }

    //------------------------REPORTES------------------------
    @GetMapping("/ventas-mensuales")
    public List<VentaPorMesDTO> getVentasPorMes() {
        return ventaServicio.obtenerVentasPorMes();
    }

    @GetMapping("/productos-mas-vendidos")
    public List<ProductoRankingDTO> getProductosMasVendidos() {
        return ventaServicio.obtenerProductosMasVendidos();
    }

    @GetMapping("/mes-con-mas-ventas")
    public VentaPorMesDTO getMesConMasVentas() {
        return ventaServicio.obtenerMesConMasVentas();
    }
}
