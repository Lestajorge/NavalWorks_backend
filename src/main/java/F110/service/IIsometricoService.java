package F110.service;

import F110.model.Isometrico;
import F110.model.EstadoIsometrico;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IIsometricoService {

    List<Isometrico> findAll();

    Isometrico findById(Long id);

    Isometrico findByCodigoIsometrico(String codigoIsometrico);

    List<Isometrico> findByBloqueId(Long bloqueId);



    List<Isometrico> findAllForAdmin();

    Isometrico createDraft(Isometrico isometrico, MultipartFile file) throws IOException;

    Isometrico updateEstado(Long id, EstadoIsometrico estado);

    Isometrico save(Isometrico isometrico);

    Isometrico update(Long id, Isometrico isometrico);

    Isometrico uploadPdf(Long id, MultipartFile file) throws IOException;

    Path getPdfPath(Long id);

    void delete(Long id);
}
