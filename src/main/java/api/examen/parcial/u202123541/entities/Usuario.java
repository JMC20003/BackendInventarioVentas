package api.examen.parcial.u202123541.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "UsuarioRoles",
            joinColumns = @JoinColumn(name = "Usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "Rol_id")
    )
    private Set<Rol> roles;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Venta> ventas;

//    @JsonIgnore
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @OneToMany(mappedBy = "usuario")
//    private Set<DetalleVentaProducto> detallesVenta;
}