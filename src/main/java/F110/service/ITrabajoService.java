package F110.service;

import F110.enums.model.EstadoTrabajo;
import F110.model.Trabajo;

import java.util.List;

/** Define las operaciones disponibles para consultar y gestionar trabajos. */
public interface ITrabajoService {

    /** Obtiene todos los trabajos. */
    List<Trabajo> findAll();

    /** Busca un trabajo por identificador. */
    Trabajo findById(Long id);

    /** Obtiene los trabajos asociados al isométrico indicado. */
    List<Trabajo> findByIsometricoId(Long isometricoId);

    /** Obtiene los trabajos que tienen el estado indicado. */
    List<Trabajo> findByEstado(EstadoTrabajo estado);

    /** Obtiene los trabajos asignados al operario indicado. */
    List<Trabajo> findByOperarioId(Long operarioId);

    /** Guarda un trabajo nuevo. */
    Trabajo save(Trabajo trabajo);

    /** Actualiza los datos del trabajo indicado. */
    Trabajo update(Long id, Trabajo trabajo);

    /** Elimina el trabajo indicado. */
    void delete(Long id);
}
