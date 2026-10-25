package F110.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdministradorLoginResponseDTO {

    private String usuario;
    private String rol;
}
