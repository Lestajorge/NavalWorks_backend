package F110.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import F110.model.EstadoIsometrico;

import java.util.ArrayList;
import java.util.List;

/**
 * Datos de un isométrico transferidos entre la API y sus clientes,
 * incluyendo la referencia a su bloque y los trabajos asociados.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsometricoDTO {

    /** Identificador del isométrico. */
    private Long id;

    /** Código identificador del isométrico. */
    private String codigoIsometrico;

    /** Revisión del documento isométrico. */
    private String revision;

    /** Estado actual del isométrico. */
    private EstadoIsometrico estado;

    /** Identificador del bloque al que pertenece. */
    private Long bloqueId;

    /** URL del archivo PDF asociado. */
    private String pdfUrl;

    /** Identificadores de los trabajos asociados; comienza como lista vacía. */
    @Builder.Default
    private List<Long> trabajoIds = new ArrayList<>();
}
