package F110.service;

import F110.enums.model.EstadoTrabajo;
import F110.model.Trabajo;
import F110.repository.TrabajoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/** Implementa la lógica de negocio para consultar y gestionar trabajos. */
@Service
@RequiredArgsConstructor
public class TrabajoService implements ITrabajoService {

    private final TrabajoRepository trabajoRepository;

    /** Devuelve todos los trabajos almacenados. */
    @Override
    public List<Trabajo> findAll() {
        return trabajoRepository.findAll();
    }

    /** Busca un trabajo por identificador y lanza una excepción si no existe. */
    @Override
    public Trabajo findById(Long id) {
        return trabajoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el trabajo con id: " + id));
    }

    /** Obtiene los trabajos asociados al isométrico indicado. */
    @Override
    public List<Trabajo> findByIsometricoId(Long isometricoId) {
        return trabajoRepository.findByIsometricoId(isometricoId);
    }

    /** Obtiene los trabajos que tienen el estado indicado. */
    @Override
    public List<Trabajo> findByEstado(EstadoTrabajo estado) {
        return trabajoRepository.findByEstado(estado);
    }

    /** Obtiene los trabajos asignados al operario indicado. */
    @Override
    public List<Trabajo> findByOperarioId(Long operarioId) {
        return trabajoRepository.findByOperariosId(operarioId);
    }

    /** Persiste el trabajo recibido. */
    @Override
    public Trabajo save(Trabajo trabajo) {
        return trabajoRepository.save(trabajo);
    }

    /** Actualiza el estado, el isométrico y los operarios asignados al trabajo. */
    @Override
    public Trabajo update(Long id, Trabajo trabajo) {
        Trabajo trabajoExistente = findById(id);
        trabajoExistente.setEstado(trabajo.getEstado());
        trabajoExistente.setIsometrico(trabajo.getIsometrico());
        trabajoExistente.setOperarios(trabajo.getOperarios());
        return trabajoRepository.save(trabajoExistente);
    }

    /** Elimina el trabajo indicado, si existe. */
    @Override
    public void delete(Long id) {
        trabajoRepository.delete(findById(id));
    }
}
