package api.examen.parcial.u202123541.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }
}
