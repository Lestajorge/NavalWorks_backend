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

/**
 * Expone los endpoints relacionados con la autenticación.
 *
 * En esta clase se ofrece el inicio de sesión para administradores: se
 * comprueban sus credenciales y se confirma que la cuenta tenga el rol
 * administrador antes de responder.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String ADMIN_ROLE = "ROLE_ADMINISTRADOR";

    private final AuthenticationManager authenticationManager;

    /**
     * Comprueba las credenciales y permite iniciar sesión solo a administradores.
     *
     * Devuelve 401 si el usuario o la contraseña no son válidos, 403 si las
     * credenciales pertenecen a un usuario que no es administrador y 200 con
     * los datos del administrador si la autenticación tiene éxito.
     *
     * @param login usuario y contraseña recibidos en el cuerpo de la petición
     * @return respuesta con los datos del administrador o el estado de error
     */
    @PostMapping("/admin")
    public ResponseEntity<AdministradorLoginResponseDTO> loginAdministrador(
            @Valid @RequestBody OperarioLoginDTO login) {
        Authentication authentication;
        // Comprueba las credenciales usando el gestor de autenticación de Spring.
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsuario(), login.getPassword()));
        } catch (AuthenticationException exception) {
            // Si el usuario o la contraseña no son válidos, responde con 401.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean administrador = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(ADMIN_ROLE::equals);
        // Una autenticación válida no basta: esta ruta es exclusiva de administradores.
        if (!administrador) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new AdministradorLoginResponseDTO(
                authentication.getName(), "ADMINISTRADOR"));
    }
}
