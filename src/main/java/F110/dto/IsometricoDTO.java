package F110.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import F110.model.EstadoIsometrico;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsometricoDTO {

    private Long id;
    private String codigoIsometrico;
    private String lineaTuberia;
    private String sistema;
    private String revision;
    private EstadoIsometrico estado;
    private Long bloqueId;
    private String pdfUrl;

    @Builder.Default
    private List<Long> trabajoIds = new ArrayList<>();
}
