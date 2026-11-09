package F110.controller;

import F110.dto.BloqueDTO;
import F110.model.Bloque;
import F110.service.IBloqueService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Expone las operaciones de la API para consultar y administrar bloques.
 *
 * Recibe peticiones bajo /api/bloques y delega la lectura y los cambios
 * al servicio de bloques. Para la comunicación con la API usa BloqueDTO.
 */
@RestController
@RequestMapping("/api/bloques")
@RequiredArgsConstructor
public class BloqueController {

    private final IBloqueService bloqueService;

    /**
     * Devuelve un mensaje sencillo para comprobar que el endpoint responde.
     *
     * @return texto "Hola mundo"
     */
    @GetMapping("/HolaMundo")
    public String holaMundo() {
        return "Hola mundo";
    }

    /**
     * Busca todos los bloques y convierte cada resultado a DTO.
     *
     * @return lista de bloques en el formato de respuesta de la API
     */
    @GetMapping
    public List<BloqueDTO> findAll() {
        return bloqueService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Busca un bloque por su identificador.
     *
     * @param id identificador del bloque
     * @return datos del bloque encontrado
     */
    @GetMapping("/{id}")
    public BloqueDTO findById(@PathVariable Long id) {

        return toDTO(bloqueService.findById(id));
    }

    /**
     * Busca un bloque por su código.
     *
     * @param codigoBloque código que identifica al bloque
     * @return datos del bloque encontrado
     */
    @GetMapping("/codigo/{codigoBloque}")
    public BloqueDTO findByCodigo(@PathVariable String codigoBloque) {
        return toDTO(bloqueService.findByCodigoBloque(codigoBloque));
    }

    /**
     * Crea un bloque con los datos recibidos y responde con estado 201.
     *
     * La anotación @Valid comprueba las restricciones declaradas en el DTO
     * antes de guardar el bloque.
     *
     * @param dto datos del bloque que se va a crear
     * @return respuesta con el bloque creado
     */
    @PostMapping
    public ResponseEntity<BloqueDTO> save(@Valid @RequestBody BloqueDTO dto) {
        Bloque bloque = new Bloque();
        bloque.setCodigoBloque(dto.getCodigoBloque());
        bloque.setZona(dto.getZona());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDTO(bloqueService.save(bloque)));
    }

    /**
     * Actualiza los datos del bloque indicado.
     *
     * @param id identificador del bloque que se va a actualizar
     * @param dto nuevos datos del bloque
     * @return datos del bloque actualizado
     */
    @PutMapping("/{id}")
    public BloqueDTO update(@PathVariable Long id, @Valid @RequestBody BloqueDTO dto) {
        Bloque bloque = new Bloque();
        bloque.setCodigoBloque(dto.getCodigoBloque());
        bloque.setZona(dto.getZona());
        return toDTO(bloqueService.update(id, bloque));
    }

    /**
     * Elimina el bloque indicado y responde sin contenido.
     *
     * @param id identificador del bloque que se va a eliminar
     * @return respuesta con estado 204 y sin cuerpo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bloqueService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convierte una entidad Bloque al DTO que se devuelve desde la API.
     *
     * @param bloque entidad que se va a convertir
     * @return DTO con el identificador, código y zona del bloque
     */
    private BloqueDTO toDTO(Bloque bloque) {
        return BloqueDTO.builder()
                .id(bloque.getId())
                .codigoBloque(bloque.getCodigoBloque())
                .zona(bloque.getZona())
                .build();
    }
}
