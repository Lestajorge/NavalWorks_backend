package F110.model;

import F110.model.Operario;
import F110.enums.model.EstadoTrabajo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trabajos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTrabajo estado; // PENDIENTE, EN_EJECUCION, REVISADO, ACEPTADO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isometrico_id", nullable = false)
    private Isometrico isometrico;

    @ManyToMany
    @JoinTable(
            name = "trabajo_operarios",
            joinColumns = @JoinColumn(name = "trabajo_id"),
            inverseJoinColumns = @JoinColumn(name = "operario_id")
    )
    @Builder.Default
    private Set<Operario> operarios = new HashSet<>();

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    @PreUpdate
    public void actualizarFecha() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}