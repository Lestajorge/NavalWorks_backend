package F110.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa un bloque de la instalación.
 * Se almacena en la tabla {@code bloques}.
 */
@Entity
@Table(name = "bloques")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bloque {

    /** Identificador generado por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Código único que identifica al bloque. */
    @Column(name = "codigo_bloque", nullable = false, unique = true, length = 50)
    private String codigoBloque;

    /** Número de zona del bloque; debe estar entre 1 y 9. */
    @Min(value = 1, message = "La zona debe ser como mínimo 1")
    @Max(value = 9, message = "La zona debe ser como máximo 9")
    @Column(name = "zona", nullable = false)
    private int zona;

    /** Isométricos asociados a este bloque. */
    @OneToMany(mappedBy = "bloque")
    @Builder.Default
    private List<Isometrico> isometricos = new ArrayList<>();
}
