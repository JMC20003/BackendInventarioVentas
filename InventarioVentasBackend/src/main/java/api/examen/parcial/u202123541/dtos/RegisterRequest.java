package api.examen.parcial.u202123541.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
}
