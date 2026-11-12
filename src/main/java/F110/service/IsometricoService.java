package F110.service;

import F110.model.Isometrico;
import F110.model.EstadoIsometrico;
import F110.repository.IsometricoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/** Implementa la lógica de negocio para isométricos, estados y archivos PDF. */
@Service
@RequiredArgsConstructor
public class IsometricoService implements IIsometricoService {

    /** Directorio local donde se guardan los PDF de los isométricos. */
    private static final Path PDF_DIRECTORY = Paths.get("uploads", "isometricos");

    private final IsometricoRepository isometricoRepository;

    /** Devuelve únicamente los isométricos publicados. */
    @Override
    public List<Isometrico> findAll() {
        return isometricoRepository.findByEstado(EstadoIsometrico.PUBLICADO);
    }

    /** Busca un isométrico publicado por identificador. */
    @Override
    public Isometrico findById(Long id) {
        return isometricoRepository.findByIdAndEstado(id, EstadoIsometrico.PUBLICADO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el isométrico con id: " + id));
    }

    /** Busca un isométrico publicado por código. */
    @Override
    public Isometrico findByCodigoIsometrico(String codigoIsometrico) {
        return isometricoRepository.findByCodigoIsometricoAndEstado(
                        codigoIsometrico, EstadoIsometrico.PUBLICADO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el isométrico con código: " + codigoIsometrico));
    }

    /** Busca los isométricos publicados del bloque indicado. */
    @Override
    public List<Isometrico> findByBloqueId(Long bloqueId) {
        return isometricoRepository.findByBloqueIdAndEstado(
                bloqueId, EstadoIsometrico.PUBLICADO);
    }




    /** Devuelve todos los isométricos, sin filtrar por estado, para administración. */
    @Override
    public List<Isometrico> findAllForAdmin() {
        return isometricoRepository.findAll();
    }

    /** Valida el PDF, crea el isométrico como borrador y lo publica tras guardar el archivo. */
    @Override
    public Isometrico createPublished(Isometrico isometrico, MultipartFile file) throws IOException {
        validatePdf(file);
        isometrico.setEstado(EstadoIsometrico.BORRADOR);
        Isometrico saved = isometricoRepository.save(isometrico);
        Isometrico uploaded = uploadPdf(saved.getId(), file);
        uploaded.setEstado(EstadoIsometrico.PUBLICADO);
        return isometricoRepository.save(uploaded);
    }

    /** Actualiza el estado; impide usar BORRADOR y publicar si falta el PDF. */
    @Override
    public Isometrico updateEstado(Long id, EstadoIsometrico estado) {
        if (estado == null || estado == EstadoIsometrico.BORRADOR) {
            throw new IllegalArgumentException("El estado debe ser PUBLICADO o ARCHIVADO");
        }

        Isometrico isometrico = isometricoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el isométrico con id: " + id));
        if (estado == EstadoIsometrico.PUBLICADO && !hasPdfUrl(isometrico)) {
            throw new IllegalStateException("No se puede publicar un isométrico sin PDF");
        }

        isometrico.setEstado(estado);
        return isometricoRepository.save(isometrico);
    }

    /** Guarda el isométrico como publicado si tiene PDF y como borrador si no lo tiene. */
    @Override
    public Isometrico save(Isometrico isometrico) {
        isometrico.setEstado(hasPdfUrl(isometrico)
                ? EstadoIsometrico.PUBLICADO
                : EstadoIsometrico.BORRADOR);
        return isometricoRepository.save(isometrico);
    }

    /** Actualiza el código y el bloque de un isométrico publicado. */
    @Override
    public Isometrico update(Long id, Isometrico isometrico) {
        Isometrico isometricoExistente = findById(id);
        isometricoExistente.setCodigoIsometrico(isometrico.getCodigoIsometrico());

        isometricoExistente.setBloque(isometrico.getBloque());
        return isometricoRepository.save(isometricoExistente);
    }

    /** Valida y guarda el PDF, asociando su ruta al isométrico. */
    @Override
    public Isometrico uploadPdf(Long id, MultipartFile file) throws IOException {
        validatePdf(file);

        Isometrico isometrico = isometricoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el isométrico con id: " + id));
        Files.createDirectories(PDF_DIRECTORY);

        String originalFilename = file.getOriginalFilename();
        String filename = id + "-" + Path.of(originalFilename).getFileName();
        Path destination = PDF_DIRECTORY.resolve(filename);
        file.transferTo(destination);

        isometrico.setPdfUrl("/uploads/isometricos/" + filename);
        return isometricoRepository.save(isometrico);
    }

    /** Comprueba que el archivo exista y que su nombre termine en {@code .pdf}. */
    private void validatePdf(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo PDF no puede estar vacío");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("El archivo debe tener extensión .pdf");
        }
    }

    /** Indica si el isométrico tiene una URL de PDF no vacía. */
    private boolean hasPdfUrl(Isometrico isometrico) {
        return isometrico.getPdfUrl() != null && !isometrico.getPdfUrl().isBlank();
    }

    /** Resuelve la ruta del PDF y falla si el isométrico o el archivo no existen. */
    @Override
    public Path getPdfPath(Long id) {
        Isometrico isometrico = findById(id);
        if (isometrico.getPdfUrl() == null || isometrico.getPdfUrl().isBlank()) {
            throw new EntityNotFoundException("El isométrico no tiene un PDF asociado");
        }

        Path pdfPath = Paths.get(isometrico.getPdfUrl().substring(1));
        if (!Files.exists(pdfPath)) {
            throw new EntityNotFoundException("No se encuentra el archivo PDF del isométrico");
        }

        return pdfPath;
    }

    /** Elimina el isométrico publicado indicado. */
    @Override
    public void delete(Long id) {
        isometricoRepository.delete(findById(id));
    }
}
