package F110.dto;

import F110.enums.model.Especialista;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos necesarios para crear un operario, incluidos los datos de acceso
 * que se usarán para registrar su cuenta.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperarioCreateDTO {

    /** Nombre del operario que se va a crear. */
    private String nombre;

    /** Apellidos del operario que se va a crear. */
    private String apellidos;

    /** Especialidad que se asignará al operario. */
    private Especialista especialista;

    /** Nombre de usuario para la cuenta del operario. */
    private String usuario;

    /** Contraseña inicial de la cuenta del operario. */
    private String password;
}
