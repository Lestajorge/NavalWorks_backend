package F110.controller;

import F110.dto.OperarioDTO;
import F110.dto.OperarioCreateDTO;
import F110.model.Operario;
import F110.service.IOperarioService;
import F110.enums.model.Especialista;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone las operaciones de la API para consultar y administrar operarios.
 *
 * Recibe peticiones bajo /api/operarios, delega las operaciones al servicio
 * y convierte las entidades al formato OperarioDTO.
 */
@RestController
@RequestMapping("/api/operarios")
@RequiredArgsConstructor
public class OperarioController {

    private final IOperarioService operarioService;

    /**
     * Devuelve todos los operarios.
     *
     * @return lista de operarios en el formato de respuesta de la API
     */
    @GetMapping
    public List<OperarioDTO> findAll() {
        return operarioService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Busca un operario por su identificador.
     *
     * @param id identificador del operario
     * @return datos del operario encontrado
     */
    @GetMapping("/{id}")
    public OperarioDTO findById(@PathVariable Long id) {
        return toDTO(operarioService.findById(id));
    }

    /**
     * Busca los operarios que tienen la especialidad indicada.
     *
     * @param especialista especialidad por la que se filtran los operarios
     * @return lista de operarios con esa especialidad
     */
    @GetMapping("/especialista/{especialista}")
    public List<OperarioDTO> findByEspecialista(@PathVariable Especialista especialista) {
        return operarioService.findByEspecialista(especialista).stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Busca operarios por su nombre.
     *
     * @param nombre nombre por el que se filtran los operarios
     * @return lista de operarios que coinciden con el nombre
     */
    @GetMapping("/nombre/{nombre}")
    public List<OperarioDTO> findByNombre(@PathVariable String nombre) {
        return operarioService.findByNombre(nombre).stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Busca operarios por sus apellidos.
     *
     * @param apellidos apellidos por los que se filtran los operarios
     * @return lista de operarios que coinciden con los apellidos
     */
    @GetMapping("/apellidos/{apellidos}")
    public List<OperarioDTO> findByApellidos(@PathVariable String apellidos) {
        return operarioService.findByApellidos(apellidos).stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Crea un operario con los datos recibidos y responde con estado 201.
     *
     * @param dto datos necesarios para crear el operario
     * @return respuesta con el operario creado
     */
    @PostMapping
    public ResponseEntity<OperarioDTO> save(@RequestBody OperarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDTO(operarioService.save(toEntity(dto))));
    }

    /**
     * Actualiza los datos del operario indicado.
     *
     * @param id identificador del operario que se va a actualizar
     * @param dto nuevos datos del operario
     * @return datos del operario actualizado
     */
    @PutMapping("/{id}")
    public OperarioDTO update(@PathVariable Long id, @RequestBody OperarioCreateDTO dto) {
        return toDTO(operarioService.update(id, toEntity(dto)));
    }

    /**
     * Elimina el operario indicado y responde sin contenido.
     *
     * @param id identificador del operario que se va a eliminar
     * @return respuesta con estado 204 y sin cuerpo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        operarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convierte los datos de creación a una entidad Operario.
     *
     * @param dto DTO con los datos necesarios para crear un operario
     * @return entidad con los datos recibidos
     */
    private Operario toEntity(OperarioCreateDTO dto) {
        Operario operario = new Operario();
        operario.setNombre(dto.getNombre());
        operario.setApellidos(dto.getApellidos());
        operario.setUsuario(dto.getUsuario());
        operario.setPassword(dto.getPassword());
        operario.setEspecialista(dto.getEspecialista());
        return operario;
    }

    /**
     * Convierte los datos de un operario a una entidad Operario.
     *
     * @param dto DTO con los datos del operario
     * @return entidad con los datos recibidos
     */
    private Operario toEntity(OperarioDTO dto) {
        Operario operario = new Operario();
        operario.setNombre(dto.getNombre());
        operario.setApellidos(dto.getApellidos());
        operario.setEspecialista(dto.getEspecialista());
        return operario;
    }

    /**
     * Convierte una entidad Operario al DTO que se devuelve desde la API.
     *
     * @param operario entidad que se va a convertir
     * @return DTO con el identificador, nombre, apellidos y especialidad
     */
    private OperarioDTO toDTO(Operario operario) {
        return OperarioDTO.builder()
                .id(operario.getId())
                .nombre(operario.getNombre())
                .apellidos(operario.getApellidos())
                .especialista(operario.getEspecialista())
                .build();
    }
}
