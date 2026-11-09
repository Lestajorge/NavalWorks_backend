package F110.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

/**
 * Credenciales recibidas en una petición de inicio de sesión.
 * Ambos valores deben estar presentes y no pueden contener solo espacios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperarioLoginDTO {

    /** Nombre de usuario enviado para autenticarse. */
    @NotBlank
    private String usuario;

    /** Contraseña enviada para autenticarse. */
    @NotBlank
    private String password;
}
