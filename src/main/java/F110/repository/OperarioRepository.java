package F110.repository;

import F110.model.Operario;
import F110.enums.model.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos de operarios.
 * Spring Data JPA implementa las consultas a partir de los nombres de los métodos.
 */
@Repository
public interface OperarioRepository extends JpaRepository<Operario, Long> {

    /** Busca los operarios que tienen la especialidad indicada. */
    List<Operario> findByEspecialista(Especialista especialista);

    /** Obtiene todas las cuentas que no son de administrador. */
    List<Operario> findByAdministradorFalse();

    /** Busca un operario de plantilla por identificador. */
    Optional<Operario> findByIdAndAdministradorFalse(Long id);

    /** Elimina las credenciales de los registros que no son administradores. */
    @Modifying
    @Transactional
    @Query("update Operario o set o.usuario = null, o.password = null where o.administrador = false")
    int clearCredentialsForNonAdministrators();

    /** Busca operarios que no son administradores y tienen la especialidad indicada. */
    List<Operario> findByAdministradorFalseAndEspecialista(Especialista especialista);

    /** Busca operarios no administradores cuyo nombre contiene el texto, sin distinguir mayúsculas. */
    List<Operario> findByAdministradorFalseAndNombreContainingIgnoreCase(String nombre);

    /** Busca operarios no administradores cuyos apellidos contienen el texto, sin distinguir mayúsculas. */
    List<Operario> findByAdministradorFalseAndApellidosContainingIgnoreCase(String apellidos);

    /** Busca operarios cuyo nombre contiene el texto, sin distinguir mayúsculas. */
    List<Operario> findByNombreContainingIgnoreCase(String nombre);

    /** Busca operarios cuyos apellidos contienen el texto, sin distinguir mayúsculas. */
    List<Operario> findByApellidosContainingIgnoreCase(String apellidos);

    /** Busca una cuenta por su nombre de usuario. */
    Optional<Operario> findByUsuario(String usuario);
}
