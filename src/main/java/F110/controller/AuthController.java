package F110.controller;

import F110.dto.AdministradorLoginResponseDTO;
import F110.dto.OperarioLoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String ADMIN_ROLE = "ROLE_ADMINISTRADOR";

    private final AuthenticationManager authenticationManager;

    @PostMapping("/admin")
    public ResponseEntity<AdministradorLoginResponseDTO> loginAdministrador(
            @Valid @RequestBody OperarioLoginDTO login) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsuario(), login.getPassword()));
        } catch (AuthenticationException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean administrador = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(ADMIN_ROLE::equals);
        if (!administrador) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new AdministradorLoginResponseDTO(
                authentication.getName(), "ADMINISTRADOR"));
    }
}
