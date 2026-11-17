package F110.model;

import F110.enums.model.Especialista;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad JPA que representa a un operario o una cuenta administradora.
 * Se almacena en la tabla {@code operarios}.
 */
@Entity
@Table(name = "operarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operario {

    /** Identificador generado por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del operario. */
    @Column(nullable = false, length = 100)
    private String nombre;

    /** Apellidos del operario. */
    @Column(nullable = false, length = 100)
    private String apellidos;

    /** Nombre de usuario; solo las cuentas administradoras tienen credenciales. */
    @Column(nullable = true, unique = true, length = 50)
    private String usuario;

    /** Contraseña almacenada como hash; es nula para operarios sin cuenta. */
    @Column(name = "password_hash", nullable = true, length = 100)
    private String password;

    /** Indica si la cuenta tiene permisos de administrador. */
    @Column(nullable = false)
    private boolean administrador;

    /** Especialidad asignada al operario, almacenada como texto en la base de datos. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Especialista especialista;

    /** Trabajos asociados; la relación se define en el campo {@code operarios} de Trabajo. */
    @ManyToMany(mappedBy = "operarios")
    @Builder.Default
    private Set<Trabajo> trabajos = new HashSet<>();
}
