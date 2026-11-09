package F110.service;

import F110.model.Operario;
import F110.repository.OperarioRepository;
import F110.enums.model.Especialista;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/** Implementa la lógica de negocio para la gestión de operarios y sus credenciales. */
@Service
@RequiredArgsConstructor
public class OperarioService implements IOperarioService {

    private final OperarioRepository operarioRepository;
    private final PasswordEncoder passwordEncoder;

    /** Obtiene únicamente las cuentas que no son de administrador. */
    @Override
    public List<Operario> findAll() {
        return operarioRepository.findByAdministradorFalse();
    }

    /** Busca un operario por identificador y lanza una excepción si no existe. */
    @Override
    public Operario findById(Long id) {
        return operarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el operario con id: " + id));
    }

    /** Busca operarios no administradores por especialidad. */
    @Override
    public List<Operario> findByEspecialista(Especialista especialista) {
        return operarioRepository.findByAdministradorFalseAndEspecialista(especialista);
    }

    /** Busca operarios no administradores cuyo nombre contiene el texto indicado. */
    @Override
    public List<Operario> findByNombre(String nombre) {
        return operarioRepository.findByAdministradorFalseAndNombreContainingIgnoreCase(nombre);
    }

    /** Busca operarios no administradores cuyos apellidos contienen el texto indicado. */
    @Override
    public List<Operario> findByApellidos(String apellidos) {
        return operarioRepository.findByAdministradorFalseAndApellidosContainingIgnoreCase(apellidos);
    }

    /** Codifica la contraseña antes de guardar el operario. */
    @Override
    public Operario save(Operario operario) {
        operario.setPassword(passwordEncoder.encode(operario.getPassword()));
        return operarioRepository.save(operario);
    }

    /** Actualiza los datos del operario y solo cambia la contraseña si se proporciona una nueva. */
    @Override
    public Operario update(Long id, Operario operario) {
        Operario operarioExistente = findById(id);
        operarioExistente.setNombre(operario.getNombre());
        operarioExistente.setApellidos(operario.getApellidos());
        operarioExistente.setUsuario(operario.getUsuario());
        operarioExistente.setEspecialista(operario.getEspecialista());
        if (operario.getPassword() != null && !operario.getPassword().isBlank()) {
            operarioExistente.setPassword(passwordEncoder.encode(operario.getPassword()));
        }
        return operarioRepository.save(operarioExistente);
    }

    /** Elimina el operario indicado, si existe. */
    @Override
    public void delete(Long id) {
        operarioRepository.delete(findById(id));
    }
}
