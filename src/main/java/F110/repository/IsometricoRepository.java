package F110.repository;

import F110.model.Isometrico;
import F110.model.EstadoIsometrico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IsometricoRepository extends JpaRepository<Isometrico, Long> {
    Optional<Isometrico> findByCodigoIsometrico(String codigoIsometrico);
    List<Isometrico> findByBloqueId(Long bloqueId);

    List<Isometrico> findByEstado(EstadoIsometrico estado);
    List<Isometrico> findByBloqueIdAndEstado(Long bloqueId, EstadoIsometrico estado);
    Optional<Isometrico> findByIdAndEstado(Long id, EstadoIsometrico estado);
    Optional<Isometrico> findByCodigoIsometricoAndEstado(String codigoIsometrico, EstadoIsometrico estado);
}