package F110.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperarioLoginDTO {

    @NotBlank
    private String usuario;

    @NotBlank
    private String password;
}
