package F110.dto;

import F110.enums.model.Especialista;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de plantilla necesarios para crear o actualizar un operario.
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

    /** Nombre de usuario opcional para cuentas de encargado. */
    private String usuario;

    /** Contraseña en texto claro recibida para codificarla antes de guardarla. */
    private String password;

    /** Valor 1 para conceder permisos de administrador y 0 para un operario normal. */
    @Min(0)
    @Max(1)
    private Integer administrador;

}
