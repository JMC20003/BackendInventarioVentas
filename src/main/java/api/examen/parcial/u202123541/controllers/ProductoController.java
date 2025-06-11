package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.dtos.ProductoCardDTO;
import api.examen.parcial.u202123541.dtos.ProductoRegistroDTO;
import api.examen.parcial.u202123541.entities.Producto;
import api.examen.parcial.u202123541.services.ProductoService;
import io.github.classgraph.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"https://proyecto5-e5bb9.web.app","http://localhost:4200"})
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    private final Path rutaImagenes = Paths.get("imagenes");

    public ProductoController() {
        try {
            Files.createDirectories(rutaImagenes); // Crea la carpeta si no existe
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear la carpeta para imágenes", e);
        }
    }
    // Obtener todos los productos
    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> productos = productoService.getAllProductos();
        return ResponseEntity.ok(productos);
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoCardDTO> obtenerProductoPorId(@PathVariable Long id) {
        ProductoCardDTO producto = productoService.getDetalleCardPorId(id);
        return ResponseEntity.ok(producto);
    }

//    // Registrar un nuevo producto
//    @PostMapping("/registrar")
//    public ResponseEntity<Producto> registrarProducto(@RequestBody Producto producto) {
//        Producto productoRegistrado = productoService.save(producto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(productoRegistrado);
//    }

    // Actualizar un producto
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // Eliminar un producto
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /* Registrar UNA talla -> devuelve card actualizada */
    @PostMapping("/registrar")
    public ResponseEntity<ProductoCardDTO> registrar(@RequestBody ProductoRegistroDTO dto) {
        return ResponseEntity.ok(productoService.registrar(dto));
    }

    /* Obtener todas las cards agregadas */
    @GetMapping("/cards")
    public ResponseEntity<List<ProductoCardDTO>> obtenerCards() {
        return ResponseEntity.ok(productoService.obtenerCards());
    }


}
