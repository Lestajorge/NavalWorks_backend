package F110.dto;

import F110.enums.model.EstadoTrabajo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Datos de un trabajo y sus referencias a un isométrico y a los operarios
 * asignados, usados para transferir información mediante la API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabajoDTO {

    /** Identificador del trabajo. */
    private Long id;

    /** Estado actual del trabajo. */
    private EstadoTrabajo estado;

    /** Identificador del isométrico asociado. */
    private Long isometricoId;

    /** Identificadores de los operarios asignados; comienza como conjunto vacío. */
    @Builder.Default
    private Set<Long> operarioIds = new HashSet<>();

    /** Fecha y hora de la última actualización del trabajo. */
    private LocalDateTime fechaActualizacion;
}
