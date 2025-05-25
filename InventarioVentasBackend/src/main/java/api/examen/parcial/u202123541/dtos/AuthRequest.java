package api.examen.parcial.u202123541.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}
