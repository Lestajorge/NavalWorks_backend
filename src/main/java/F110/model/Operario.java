package F110.model;

import com.navantia.f110.enums.model.Especialista;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "operarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private boolean administrador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Especialista especialista;

    @ManyToMany(mappedBy = "operarios")
    @Builder.Default
    private Set<Trabajo> trabajos = new HashSet<>();
}
