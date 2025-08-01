package api.examen.parcial.u202123541.controllers;

import api.examen.parcial.u202123541.dtos.ProductoDTO;
import api.examen.parcial.u202123541.dtos.ProductoRegistroDTO;
import api.examen.parcial.u202123541.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"https://tiendarjsc.site","http://localhost:4200","https://backoffice.tiendarjsc.site"})
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private static final String RUTA_IMAGENES = "uploads/";

    @PostMapping("/upload-imagen")
    public ResponseEntity<String> uploadImagen(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Archivo vacío");
            }

            // Crear directorio si no existe
            File directorio = new File(RUTA_IMAGENES);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            // Validar nombre del archivo (limpio)
            String originalFilename = Paths.get(file.getOriginalFilename()).getFileName().toString();
            String nombreArchivo = UUID.randomUUID() + "_" + originalFilename;

            Path rutaArchivo = Paths.get(RUTA_IMAGENES, nombreArchivo);
            Files.copy(file.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            // Devolver ruta relativa para usar desde frontend
            String url = "/api/productos/imagenes/" + nombreArchivo;
            return ResponseEntity.ok(url);

        } catch (IOException e) {
            e.printStackTrace(); // 👈 para depurar en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir la imagen");
        }
    }

    @GetMapping("/imagenes/{nombre}")
    public ResponseEntity<Resource> verImagen(@PathVariable String nombre) {
        try {
            Path ruta = Paths.get(RUTA_IMAGENES).resolve(nombre).toAbsolutePath();
            UrlResource recurso = new UrlResource(ruta.toUri());

            if (!recurso.exists() || !recurso.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(ruta);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(recurso); // YA ESTÁ BIEN

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


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
