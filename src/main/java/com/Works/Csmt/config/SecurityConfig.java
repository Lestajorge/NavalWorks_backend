package com.Works.Csmt.config;

import F110.model.Operario;
import F110.repository.OperarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/**
 * Configura cómo se autentican los usuarios y qué permisos necesita cada petición.
 *
 * Obtiene los datos de acceso de los operarios guardados en el repositorio y
 * define las reglas de acceso para los endpoints de la API. La autenticación
 * usa HTTP Basic y no mantiene sesiones entre peticiones.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OperarioRepository operarioRepository;

    /**
     * Expone el gestor de autenticación configurado por Spring Security.
     *
     * Otros componentes pueden usarlo para comprobar las credenciales de un
     * usuario, por ejemplo durante el inicio de sesión.
     *
     * @param authenticationConfiguration configuración de autenticación de Spring
     * @return gestor que valida las credenciales
     * @throws Exception si Spring no puede obtener el gestor de autenticación
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Busca las cuentas en la base de datos cuando Spring necesita autenticar
     * a alguien y las convierte al formato que entiende Spring Security.
     *
     * El rol asignado depende de si el operario está marcado como administrador.
     *
     * @return servicio que carga un usuario a partir de su nombre de usuario
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Operario operario = operarioRepository.findByUsuario(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "No existe el usuario: " + username));

            return User.withUsername(operario.getUsuario())
                    .password(operario.getPassword())
                    .roles(operario.isAdministrador() ? "ADMINISTRADOR" : "OPERARIO")
                    .build();
        };
    }

    /**
     * Define las reglas de seguridad para las peticiones HTTP de la aplicación.
     *
     * Los administradores pueden usar todos los endpoints de la API. Los
     * operarios pueden consultar operarios, hacer cualquier operación sobre
     * trabajos y consultar isométricos. Para el resto de endpoints se exige el
     * rol de administrador. El inicio de sesión de administrador es público.
     * Usa HTTP Basic, no crea sesiones y responde con estado 401 si falta la
     * autenticación requerida.
     *
     * @param http configuración HTTP de Spring Security
     * @return cadena de filtros que aplica las reglas de seguridad
     * @throws Exception si no se puede construir la configuración de seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/admin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/isometricos/admin").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/operarios", "/api/operarios/**")
                        .hasAnyRole("ADMINISTRADOR", "OPERARIO")
                        .requestMatchers(HttpMethod.GET, "/api/trabajos", "/api/trabajos/**")
                        .hasAnyRole("ADMINISTRADOR", "OPERARIO")
                        .requestMatchers(HttpMethod.POST, "/api/trabajos", "/api/trabajos/**")
                        .hasAnyRole("ADMINISTRADOR", "OPERARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/trabajos", "/api/trabajos/**")
                        .hasAnyRole("ADMINISTRADOR", "OPERARIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/trabajos", "/api/trabajos/**")
                        .hasAnyRole("ADMINISTRADOR", "OPERARIO")
                        .requestMatchers(HttpMethod.GET, "/api/isometricos", "/api/isometricos/**")
                        .hasAnyRole("ADMINISTRADOR", "OPERARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/isometricos", "/api/isometricos/**")
                        .hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/**").hasRole("ADMINISTRADOR")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                );

        return http.build();
    }
}
