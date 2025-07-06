package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.dtos.ProductoDTO;
import api.examen.parcial.u202123541.dtos.ProductoRegistroDTO;
import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = {"https://tiendarjsc.site","http://localhost:4200","https://backoffice.tiendarjsc.site"})
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/registrar")
    public ResponseEntity<ProductoDTO> registrarProducto(@RequestBody ProductoRegistroDTO dto) {
        ProductoDTO creado = productoService.registrarProducto(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRegistroDTO dto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    //AGREGAR CONTROLLERS PARA REPORTES

}
