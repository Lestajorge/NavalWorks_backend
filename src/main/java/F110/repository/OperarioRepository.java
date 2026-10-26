package F110.repository;

import F110.model.Operario;
import com.navantia.f110.enums.model.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperarioRepository extends JpaRepository<Operario, Long> {

    List<Operario> findByEspecialista(Especialista especialista);

    List<Operario> findByAdministradorFalse();

    List<Operario> findByAdministradorFalseAndEspecialista(Especialista especialista);

    List<Operario> findByAdministradorFalseAndNombreContainingIgnoreCase(String nombre);

    List<Operario> findByAdministradorFalseAndApellidosContainingIgnoreCase(String apellidos);

    List<Operario> findByNombreContainingIgnoreCase(String nombre);

    List<Operario> findByApellidosContainingIgnoreCase(String apellidos);

    Optional<Operario> findByUsuario(String usuario);
}
