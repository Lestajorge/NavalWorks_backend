package F110.service;

import F110.model.Operario;
import F110.repository.OperarioRepository;
import com.navantia.f110.enums.model.Especialista;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperarioService implements IOperarioService {

    private final OperarioRepository operarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Operario> findAll() {
        return operarioRepository.findByAdministradorFalse();
    }

    @Override
    public Operario findById(Long id) {
        return operarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el operario con id: " + id));
    }

    @Override
    public List<Operario> findByEspecialista(Especialista especialista) {
        return operarioRepository.findByAdministradorFalseAndEspecialista(especialista);
    }

    @Override
    public List<Operario> findByNombre(String nombre) {
        return operarioRepository.findByAdministradorFalseAndNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Operario> findByApellidos(String apellidos) {
        return operarioRepository.findByAdministradorFalseAndApellidosContainingIgnoreCase(apellidos);
    }

    @Override
    public Operario save(Operario operario) {
        operario.setPassword(passwordEncoder.encode(operario.getPassword()));
        return operarioRepository.save(operario);
    }

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

    @Override
    public void delete(Long id) {
        operarioRepository.delete(findById(id));
    }
}
