package F110.enums.model;

import lombok.Getter;

/**
 * Especialidades que se pueden asignar a un operario.
 * Cada valor incluye una descripción legible para mostrar en la aplicación.
 */
@Getter
public enum Especialista {
    /** Especialista en trabajos de tubería y montaje. */
    TUBERO("Oficial de Tubería y Montaje"),

    /** Especialista en trabajos de soldadura. */
    SOLDADOR("Oficial de Soldadura"),

    /** Responsable o supervisor de los trabajos. */
    ENCARGADO("Encargado");

    /** Descripción legible de la especialidad. */
    private final String descripcion;

    Especialista(String descripcion) {
        this.descripcion = descripcion;
    }
}
