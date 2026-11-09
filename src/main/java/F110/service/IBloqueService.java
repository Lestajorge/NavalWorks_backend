package F110.service;

import F110.model.Bloque;

import java.util.List;

/** Define las operaciones disponibles para gestionar bloques. */
public interface IBloqueService {

    /** Obtiene todos los bloques. */
    List<Bloque> findAll();

    /** Busca un bloque por su identificador; falla si no existe. */
    Bloque findById(Long id);

    /** Busca un bloque por su código; falla si no existe. */
    Bloque findByCodigoBloque(String codigoBloque);

    /** Guarda un bloque nuevo o persiste sus cambios. */
    Bloque save(Bloque bloque);

    /** Actualiza el código y la zona del bloque indicado. */
    Bloque update(Long id, Bloque bloque);

    /** Elimina el bloque indicado; falla si no existe. */
    void delete(Long id);
}
