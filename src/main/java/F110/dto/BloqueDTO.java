package F110.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Datos de un bloque que se transfieren entre la API y sus clientes.
 * La zona debe estar comprendida entre 1 y 9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloqueDTO {

    /** Identificador del bloque. */
    private Long id;

    /** Código que identifica al bloque. */
    private String codigoBloque;

    /** Número de zona del bloque, validado entre 1 y 9. */
    @Min(value = 1, message = "La zona debe ser como mínimo 1")
    @Max(value = 9, message = "La zona debe ser como máximo 9")
    private int zona;
}
