package F110.controller;

import F110.dto.IsometricoDTO;
import F110.model.EstadoIsometrico;
import F110.model.Isometrico;
import F110.service.IBloqueService;
import F110.service.IIsometricoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Expone las operaciones de la API para consultar y administrar isométricos.
 *
 * Recibe peticiones bajo /api/isometricos, delega las operaciones al servicio
 * correspondiente y convierte las entidades al formato IsometricoDTO.
 */
@RestController
@RequestMapping("/api/isometricos")
@RequiredArgsConstructor
public class IsometricoController {

    private final IIsometricoService isometricoService;
    private final IBloqueService bloqueService;

    /**
     * Devuelve todos los isométricos disponibles.
     *
     * @return lista de isométricos en el formato de respuesta de la API
     */
    @GetMapping
    public List<IsometricoDTO> findAll() {
        return isometricoService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Devuelve todos los isométricos en la vista destinada a administración.
     *
     * @return lista de isométricos para administración
     */
    @GetMapping("/admin")
    public List<IsometricoDTO> findAllForAdmin() {
        return isometricoService.findAllForAdmin().stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Busca un isométrico por su identificador.
     *
     * @param id identificador del isométrico
     * @return datos del isométrico encontrado
     */
    @GetMapping("/{id}")
    public IsometricoDTO findById(@PathVariable Long id) {
        return toDTO(isometricoService.findById(id));
    }

    /**
     * Busca un isométrico por su código.
     *
     * @param codigoIsometrico código que identifica al isométrico
     * @return datos del isométrico encontrado
     */
    @GetMapping("/codigo/{codigoIsometrico}")
    public IsometricoDTO findByCodigo(@PathVariable String codigoIsometrico) {
        return toDTO(isometricoService.findByCodigoIsometrico(codigoIsometrico));
    }

    /**
     * Busca los isométricos asociados a un bloque.
     *
     * @param bloqueId identificador del bloque
     * @return lista de isométricos asociados al bloque
     */
    @GetMapping("/bloque/{bloqueId}")
    public List<IsometricoDTO> findByBloque(@PathVariable Long bloqueId) {
        return isometricoService.findByBloqueId(bloqueId).stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Obtiene el PDF de un isométrico para mostrarlo en línea.
     *
     * @param id identificador del isométrico
     * @return respuesta con el archivo PDF
     * @throws IOException si no se puede acceder al archivo
     */
    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getPdf(@PathVariable Long id) throws IOException {
        Path pdfPath = isometricoService.getPdfPath(id);
        Resource resource = new UrlResource(pdfPath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Crea un isométrico a partir de los datos recibidos.
     *
     * @param dto datos del isométrico que se va a crear
     * @return respuesta con el isométrico creado y estado 201
     */
    @PostMapping
    public ResponseEntity<IsometricoDTO> save(@RequestBody IsometricoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDTO(isometricoService.save(toEntity(dto))));
    }

    /**
     * Crea y publica un isométrico asociado a un bloque y a un archivo PDF.
     *
     * @param codigoIsometrico código del isométrico
     * @param revision revisión del isométrico
     * @param bloqueId identificador del bloque asociado
     * @param file archivo PDF recibido
     * @return respuesta con el isométrico publicado y estado 201
     * @throws IOException si ocurre un error al procesar el archivo
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<IsometricoDTO> createPublished(
            @RequestParam String codigoIsometrico,
            @RequestParam String revision,
            @RequestParam Long bloqueId,
            @RequestParam("file") MultipartFile file) throws IOException {
        Isometrico isometrico = new Isometrico();
        isometrico.setCodigoIsometrico(codigoIsometrico);
        isometrico.setRevision(revision);
        isometrico.setBloque(bloqueService.findById(bloqueId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDTO(isometricoService.createPublished(isometrico, file)));
    }

    /**
     * Carga o reemplaza el PDF de un isométrico existente.
     *
     * @param id identificador del isométrico
     * @param file archivo PDF que se va a cargar
     * @return datos del isométrico actualizado
     * @throws IOException si ocurre un error al procesar el archivo
     */
    @PostMapping(value = "/{id}/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public IsometricoDTO uploadPdf(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        return toDTO(isometricoService.uploadPdf(id, file));
    }

    /**
     * Actualiza los datos de un isométrico existente.
     *
     * @param id identificador del isométrico que se va a actualizar
     * @param dto nuevos datos del isométrico
     * @return datos del isométrico actualizado
     */
    @PutMapping("/{id}")
    public IsometricoDTO update(@PathVariable Long id, @RequestBody IsometricoDTO dto) {
        return toDTO(isometricoService.update(id, toEntity(dto)));
    }

    /**
     * Actualiza el estado de un isométrico.
     *
     * @param id identificador del isométrico
     * @param request solicitud con el nuevo estado
     * @return datos del isométrico actualizado
     */
    @PutMapping("/{id}/estado")
    public IsometricoDTO updateEstado(
            @PathVariable Long id,
            @RequestBody EstadoRequest request) {
        return toDTO(isometricoService.updateEstado(id, request.estado()));
    }

    /**
     * Elimina el isométrico indicado y responde sin contenido.
     *
     * @param id identificador del isométrico que se va a eliminar
     * @return respuesta con estado 204 y sin cuerpo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        isometricoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convierte los datos recibidos en un DTO a una entidad Isometrico.
     *
     * @param dto DTO con los datos del isométrico
     * @return entidad con los datos del DTO y el bloque asociado
     */
    private Isometrico toEntity(IsometricoDTO dto) {
        Isometrico isometrico = new Isometrico();
        isometrico.setCodigoIsometrico(dto.getCodigoIsometrico());
        isometrico.setRevision(dto.getRevision());
        isometrico.setBloque(bloqueService.findById(dto.getBloqueId()));
        isometrico.setPdfUrl(dto.getPdfUrl());
        return isometrico;
    }

    /**
     * Convierte una entidad Isometrico al DTO que se devuelve desde la API.
     *
     * @param isometrico entidad que se va a convertir
     * @return DTO con los datos del isométrico, su bloque y sus trabajos
     */
    private IsometricoDTO toDTO(Isometrico isometrico) {
        return IsometricoDTO.builder()
                .id(isometrico.getId())
                .codigoIsometrico(isometrico.getCodigoIsometrico())
                .revision(isometrico.getRevision())
                .estado(isometrico.getEstado())
                .bloqueId(isometrico.getBloque().getId())
                .pdfUrl(isometrico.getPdfUrl())
                .trabajoIds(isometrico.getTrabajos().stream()
                        .map(trabajo -> trabajo.getId())
                        .toList())
                .build();
    }

    /**
     * Representa la solicitud para cambiar el estado de un isométrico.
     *
     */
        public static final class EstadoRequest {
        private final EstadoIsometrico estado;

        /**
         * @param estado nuevo estado del isométrico
         */
        public EstadoRequest(EstadoIsometrico estado) {
            this.estado = estado;
        }

        public EstadoIsometrico estado() {
            return estado;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (EstadoRequest) obj;
            return Objects.equals(this.estado, that.estado);
        }

        @Override
        public int hashCode() {
            return Objects.hash(estado);
        }

        @Override
        public String toString() {
            return "EstadoRequest[" +
                    "estado=" + estado + ']';
        }
    }
}
