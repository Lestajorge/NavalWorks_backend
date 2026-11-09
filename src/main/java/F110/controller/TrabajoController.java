package F110.controller;

import F110.dto.TrabajoDTO;
import F110.enums.model.EstadoTrabajo;
import F110.model.Operario;
import F110.model.Trabajo;
import F110.service.IIsometricoService;
import F110.service.IOperarioService;
import F110.service.ITrabajoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Expone las operaciones de la API para consultar y administrar trabajos.
 *
 * Recibe peticiones bajo /api/trabajos, delega las operaciones al servicio
 * y convierte las entidades al formato TrabajoDTO.
 */
@RestController
@RequestMapping("/api/trabajos")
@RequiredArgsConstructor
public class TrabajoController {

    private final ITrabajoService trabajoService;
    private final IIsometricoService isometricoService;
    private final IOperarioService operarioService;

    /**
     * Devuelve todos los trabajos.
     *
     * @return lista de trabajos en el formato de respuesta de la API
     */
    @GetMapping
    public List<TrabajoDTO> findAll() {
        return trabajoService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Busca un trabajo por su identificador.
     *
     * @param id identificador del trabajo
     * @return datos del trabajo encontrado
     */
    @GetMapping("/{id}")
    public TrabajoDTO findById(@PathVariable Long id) {
        return toDTO(trabajoService.findById(id));
    }

    /**
     * Busca los trabajos asociados a un isométrico.
     *
     * @param isometricoId identificador del isométrico
     * @return lista de trabajos asociados al isométrico
     */
    @GetMapping("/isometrico/{isometricoId}")
    public List<TrabajoDTO> findByIsometrico(@PathVariable Long isometricoId) {
        return trabajoService.findByIsometricoId(isometricoId).stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Busca los trabajos que tienen el estado indicado.
     *
     * @param estado estado por el que se filtran los trabajos
     * @return lista de trabajos con ese estado
     */
    @GetMapping("/estado/{estado}")
    public List<TrabajoDTO> findByEstado(@PathVariable EstadoTrabajo estado) {
        return trabajoService.findByEstado(estado).stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Busca los trabajos asignados a un operario.
     *
     * @param operarioId identificador del operario
     * @return lista de trabajos asignados al operario
     */
    @GetMapping("/operario/{operarioId}")
    public List<TrabajoDTO> findByOperario(@PathVariable Long operarioId) {
        return trabajoService.findByOperarioId(operarioId).stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Crea un trabajo con los datos recibidos y responde con estado 201.
     *
     * @param dto datos del trabajo que se va a crear
     * @return respuesta con el trabajo creado
     */
    @PostMapping
    public ResponseEntity<TrabajoDTO> save(@RequestBody TrabajoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDTO(trabajoService.save(toEntity(dto))));
    }

    /**
     * Actualiza los datos del trabajo indicado.
     *
     * @param id identificador del trabajo que se va a actualizar
     * @param dto nuevos datos del trabajo
     * @return datos del trabajo actualizado
     */
    @PutMapping("/{id}")
    public TrabajoDTO update(@PathVariable Long id, @RequestBody TrabajoDTO dto) {
        return toDTO(trabajoService.update(id, toEntity(dto)));
    }

    /**
     * Elimina el trabajo indicado y responde sin contenido.
     *
     * @param id identificador del trabajo que se va a eliminar
     * @return respuesta con estado 204 y sin cuerpo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trabajoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convierte los datos del DTO a una entidad Trabajo y resuelve sus relaciones.
     *
     * @param dto DTO con el estado, el isométrico y los operarios asociados
     * @return entidad con el isométrico y los operarios cargados
     */
    private Trabajo toEntity(TrabajoDTO dto) {
        Trabajo trabajo = new Trabajo();
        trabajo.setEstado(dto.getEstado());
        trabajo.setIsometrico(isometricoService.findById(dto.getIsometricoId()));
        trabajo.setOperarios(dto.getOperarioIds().stream()
                .map(operarioService::findById)
                .collect(Collectors.toCollection(HashSet::new)));
        return trabajo;
    }

    /**
     * Convierte una entidad Trabajo al DTO que se devuelve desde la API.
     *
     * @param trabajo entidad que se va a convertir
     * @return DTO con el estado, el isométrico, los operarios y la fecha de actualización
     */
    private TrabajoDTO toDTO(Trabajo trabajo) {
        return TrabajoDTO.builder()
                .id(trabajo.getId())
                .estado(trabajo.getEstado())
                .isometricoId(trabajo.getIsometrico().getId())
                .operarioIds(trabajo.getOperarios().stream()
                        .map(Operario::getId)
                        .collect(Collectors.toSet()))
                .fechaActualizacion(trabajo.getFechaActualizacion())
                .build();
    }
}
