package F110.repository;

import F110.model.Isometrico;
import F110.model.EstadoIsometrico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos de isométricos.
 * Spring Data JPA genera las consultas según los nombres de los métodos.
 */
@Repository
public interface IsometricoRepository extends JpaRepository<Isometrico, Long> {

    /** Busca un isométrico por su código único. */
    Optional<Isometrico> findByCodigoIsometrico(String codigoIsometrico);

    /** Obtiene los isométricos asociados al identificador de bloque indicado. */
    List<Isometrico> findByBloqueId(Long bloqueId);

    /** Obtiene los isométricos que se encuentran en el estado indicado. */
    List<Isometrico> findByEstado(EstadoIsometrico estado);

    /** Filtra isométricos por bloque y estado. */
    List<Isometrico> findByBloqueIdAndEstado(Long bloqueId, EstadoIsometrico estado);

    /** Busca un isométrico por identificador solo si tiene el estado indicado. */
    Optional<Isometrico> findByIdAndEstado(Long id, EstadoIsometrico estado);

    /** Busca un isométrico por código solo si tiene el estado indicado. */
    Optional<Isometrico> findByCodigoIsometricoAndEstado(String codigoIsometrico, EstadoIsometrico estado);
}