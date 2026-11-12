package F110.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa un documento isométrico y sus datos asociados.
 * Se almacena en la tabla {@code isometricos}.
 */
@Entity
@Table(name = "isometricos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Isometrico {

    /** Identificador generado por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Código único del isométrico, por ejemplo, {@code ISO-410-01-A}. */
    @Column(name = "codigo_isometrico", nullable = false, unique = true, length = 50)
    private String codigoIsometrico; // Ejemplo: "ISO-410-01-A"

    /** Estado del isométrico, guardado en la base de datos por su nombre. */
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PUBLICADO'")
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoIsometrico estado = EstadoIsometrico.BORRADOR;

    /** URL del archivo PDF asociado al isométrico. */
    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    /** Bloque al que pertenece este isométrico. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloque_id", nullable = false)
    private Bloque bloque;

    /** Trabajos asociados; se eliminan al borrar el isométrico. */
    @OneToMany(mappedBy = "isometrico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Trabajo> trabajos = new ArrayList<>();
}