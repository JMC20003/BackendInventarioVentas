package api.examen.parcial.u202123541.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoRegistroDTO {
    private String nombre;
    private String talla;
    private BigDecimal precio;
    private Integer stock;
    private String categoria;
    private String descripcion;
    private String imagen;
}