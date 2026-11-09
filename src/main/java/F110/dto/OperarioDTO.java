package F110.dto;

import F110.enums.model.Especialista;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de un operario destinados a su consulta o transferencia por la API.
 * No incluye credenciales.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperarioDTO {

    /** Identificador del operario. */
    private Long id;

    /** Nombre del operario. */
    private String nombre;

    /** Apellidos del operario. */
    private String apellidos;

    /** Especialidad asignada al operario. */
    private Especialista especialista;
}
