package F110.service;

import F110.model.Isometrico;
import F110.model.EstadoIsometrico;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** Define las operaciones de consulta y gestión de isométricos y sus archivos PDF. */
public interface IIsometricoService {

    /** Obtiene los isométricos publicados. */
    List<Isometrico> findAll();

    /** Busca un isométrico publicado por identificador. */
    Isometrico findById(Long id);

    /** Busca un isométrico publicado por código. */
    Isometrico findByCodigoIsometrico(String codigoIsometrico);

    /** Obtiene los isométricos publicados pertenecientes al bloque indicado. */
    List<Isometrico> findByBloqueId(Long bloqueId);



    /** Obtiene todos los isométricos, incluidos los no publicados, para administración. */
    List<Isometrico> findAllForAdmin();

    /** Crea un isométrico, guarda su PDF y lo publica. */
    Isometrico createPublished(Isometrico isometrico, MultipartFile file) throws IOException;

    /** Cambia el estado del isométrico, con las validaciones de publicación correspondientes. */
    Isometrico updateEstado(Long id, EstadoIsometrico estado);

    /** Guarda el isométrico estableciendo su estado según tenga o no una URL de PDF. */
    Isometrico save(Isometrico isometrico);

    /** Actualiza los datos principales de un isométrico existente. */
    Isometrico update(Long id, Isometrico isometrico);

    /** Guarda el PDF del isométrico y actualiza la URL asociada. */
    Isometrico uploadPdf(Long id, MultipartFile file) throws IOException;

    /** Devuelve la ruta local del PDF asociado al isométrico publicado. */
    Path getPdfPath(Long id);

    /** Elimina el isométrico indicado. */
    void delete(Long id);
}
