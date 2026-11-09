package F110.model;

import F110.model.Operario;
import F110.enums.model.EstadoTrabajo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad JPA que representa un trabajo asociado a un isométrico y asignado
 * a uno o más operarios. Se almacena en la tabla {@code trabajos}.
 */
@Entity
@Table(name = "trabajos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trabajo {

    /** Identificador generado por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /** Estado actual del trabajo, guardado por el nombre del enum. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTrabajo estado; // PENDIENTE, EN_EJECUCION, REVISADO, ACEPTADO

    /** Isométrico al que pertenece este trabajo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isometrico_id", nullable = false)
    private Isometrico isometrico;

    /** Operarios asignados; la relación se guarda en la tabla intermedia {@code trabajo_operarios}. */
    @ManyToMany
    @JoinTable(
            name = "trabajo_operarios",
            joinColumns = @JoinColumn(name = "trabajo_id"),
            inverseJoinColumns = @JoinColumn(name = "operario_id")
    )
    @Builder.Default
    private Set<Operario> operarios = new HashSet<>();

    /** Fecha y hora de la última actualización del trabajo. */
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /** Actualiza la fecha de modificación antes de insertar o actualizar la entidad. */
    @PrePersist
    @PreUpdate
    public void actualizarFecha() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}