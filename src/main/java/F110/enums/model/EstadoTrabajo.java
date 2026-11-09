package F110.enums.model;

import lombok.Getter;

/**
 * Estados posibles de un trabajo durante su ciclo de realización.
 * Cada estado define una descripción y un color para representarlo en la aplicación.
 */
@Getter
public enum EstadoTrabajo {
    /** El trabajo aún no ha comenzado. */
    PENDIENTE("Pendiente de inicio", "gris"),

    /** El trabajo se está realizando. */
    EN_EJECUCION("En proceso de montaje/soldadura", "naranja"),

    /** El trabajo está a la espera de una inspección de calidad. */
    REVISADO("Pendiente de inspección de calidad", "amarillo"),

    /** El trabajo ha sido validado y finalizado. */
    ACEPTADO("Trabajo validado y finalizado", "verde");

    /** Texto legible que explica el estado. */
    private final String descripcion;

    /** Color asociado al estado para su representación visual. */
    private final String color;

    EstadoTrabajo(String descripcion, String color) {
        this.descripcion = descripcion;
        this.color = color;
    }
}