package F110.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Datos que se devuelven cuando un administrador inicia sesión correctamente.
 * Spring serializa esta clase como el cuerpo JSON de la respuesta del login.
 */
@Data
@AllArgsConstructor
public class AdministradorLoginResponseDTO {

    /** Nombre del usuario autenticado. */
    private String usuario;

    /** Rol del usuario, por ejemplo, "ADMINISTRADOR". */
    private String rol;
}
