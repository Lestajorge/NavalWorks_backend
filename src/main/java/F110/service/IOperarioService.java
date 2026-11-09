package F110.service;

import F110.model.Operario;
import F110.enums.model.Especialista;

import java.util.List;

/** Define las operaciones disponibles para consultar y gestionar operarios. */
public interface IOperarioService {

    /** Obtiene los operarios que no son administradores. */
    List<Operario> findAll();

    /** Busca un operario por su identificador. */
    Operario findById(Long id);

    /** Busca los operarios no administradores de la especialidad indicada. */
    List<Operario> findByEspecialista(Especialista especialista);

    /** Busca operarios no administradores por coincidencia parcial en el nombre. */
    List<Operario> findByNombre(String nombre);

    /** Busca operarios no administradores por coincidencia parcial en los apellidos. */
    List<Operario> findByApellidos(String apellidos);

    /** Guarda un operario nuevo. */
    Operario save(Operario operario);

    /** Actualiza los datos del operario indicado. */
    Operario update(Long id, Operario operario);

    /** Elimina el operario indicado. */
    void delete(Long id);
}
