package com.Works.Csmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configura el mecanismo que la aplicación utiliza para proteger las contraseñas.
 *
 * Registra un codificador basado en BCrypt para que Spring pueda inyectarlo
 * donde se necesite guardar o comprobar una contraseña.
 */
@Configuration
public class PasswordConfig {

    /**
     * Crea el codificador BCrypt que Spring compartirá como bean.
     *
     * BCrypt genera un hash de una sola vía; la contraseña original no se
     * guarda como texto legible.
     *
     * @return codificador para generar y verificar hashes de contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
