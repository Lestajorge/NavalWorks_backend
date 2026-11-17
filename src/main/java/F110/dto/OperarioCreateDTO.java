package F110.dto;

import F110.enums.model.Especialista;
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

}
