package F110.repository;

import F110.model.Trabajo;
import F110.enums.model.EstadoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Acceso a datos de trabajos.
 * Spring Data JPA implementa las consultas derivándolas de los nombres de método.
 */
@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {

    /** Obtiene los trabajos asociados al identificador de isométrico indicado. */
    List<Trabajo> findByIsometricoId(Long isometricoId);

    /** Comprueba si otro trabajo ya está asociado al isométrico indicado. */
    boolean existsByIsometricoId(Long isometricoId);

    /** Comprueba si otro trabajo distinto del indicado usa el mismo isométrico. */
    boolean existsByIsometricoIdAndIdNot(Long isometricoId, Long id);

    /** Obtiene los trabajos que se encuentran en el estado indicado. */
    List<Trabajo> findByEstado(EstadoTrabajo estado);

    /** Obtiene los trabajos asignados al operario indicado. */
    List<Trabajo> findByOperariosId(Long operarioId);
}