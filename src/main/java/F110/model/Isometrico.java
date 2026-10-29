package F110.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "isometricos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Isometrico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_isometrico", nullable = false, unique = true, length = 50)
    private String codigoIsometrico; // Ejemplo: "ISO-410-01-A"

    @Column(name = "linea_tuberia", nullable = false, length = 50)
    private String lineaTuberia; // Ejemplo: "L-410-01"

    @Column(length = 50)
    private String sistema;

    @Column(length = 50)
    private String revision;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PUBLICADO'")
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoIsometrico estado = EstadoIsometrico.BORRADOR;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloque_id", nullable = false)
    private Bloque bloque;

    @OneToMany(mappedBy = "isometrico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Trabajo> trabajos = new ArrayList<>();
}