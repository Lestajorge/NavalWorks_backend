package F110.service;

import F110.model.Bloque;
import F110.repository.BloqueRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/** Implementa la lógica de negocio para la gestión de bloques. */
@Service
@RequiredArgsConstructor
public class BloqueService implements IBloqueService {

    private final BloqueRepository bloqueRepository;

    /** Devuelve todos los bloques almacenados. */
    @Override
    public List<Bloque> findAll() {
        return bloqueRepository.findAll();
    }

    /** Busca un bloque por identificador y lanza una excepción si no existe. */
    @Override
    public Bloque findById(Long id) {
        return bloqueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el bloque con id: " + id));
    }

    /** Busca un bloque por código y lanza una excepción si no existe. */
    @Override
    public Bloque findByCodigoBloque(String codigoBloque) {
        return bloqueRepository.findByCodigoBloque(codigoBloque)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el bloque con código: " + codigoBloque));
    }

    /** Persiste el bloque recibido. */
    @Override
    public Bloque save(Bloque bloque) {
        return bloqueRepository.save(bloque);
    }

    /** Actualiza el código y la zona de un bloque existente. */
    @Override
    public Bloque update(Long id, Bloque bloque) {
        Bloque bloqueExistente = findById(id);
        bloqueExistente.setCodigoBloque(bloque.getCodigoBloque());
        bloqueExistente.setZona(bloque.getZona());
        return bloqueRepository.save(bloqueExistente);
    }

    /** Elimina el bloque indicado, si existe. */
    @Override
    public void delete(Long id) {
        bloqueRepository.delete(findById(id));
    }
}
