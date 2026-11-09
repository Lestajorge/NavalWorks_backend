package F110.repository;

import F110.model.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Acceso a datos de bloques.
 * Hereda de JpaRepository las operaciones CRUD básicas.
 */
@Repository
public interface BloqueRepository extends JpaRepository<Bloque, Long> {

    /** Busca un bloque por su código único. */
    Optional<Bloque> findByCodigoBloque(String codigoBloque);

    /** Indica si ya existe un bloque con el código indicado. */
    boolean existsByCodigoBloque(String codigoBloque);
}