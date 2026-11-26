package com.Works.Csmt.config;

import F110.model.Operario;
import F110.repository.OperarioRepository;
import F110.enums.model.Especialista;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Prepara la cuenta administradora al arrancar la aplicación.
 *
 * Lee el usuario y la contraseña de la configuración y crea la cuenta solo si
 * todavía no existe. La cuenta queda guardada en la base de datos.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAccountInitializer implements ApplicationRunner {

    private final OperarioRepository operarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String username;

    @Value("${app.admin.password:}")
    private String password;

    /**
     * Comprueba y prepara la cuenta administradora al iniciar Spring Boot.
     *
     * Si no se ha configurado una contraseña, no crea ninguna cuenta. En caso
     * contrario, reutiliza la cuenta indicada o crea una nueva y le asigna
     * permisos de administrador.
     *
     * @param args argumentos usados para arrancar la aplicación
     */
    @Override
    public void run(ApplicationArguments args) {
        if (password.isBlank()) {
            log.info("Creación de administrador omitida: configura APP_ADMIN_PASSWORD");
            return;
        }

        Operario admin = operarioRepository.findByUsuario(username)
                .orElseGet(() -> Operario.builder()
                        .nombre("Administrador")
                        .apellidos("Sistema")
                        .usuario(username)
                        .password(passwordEncoder.encode(password))
                        .especialista(Especialista.ENCARGADO)
                        .administrador(true)
                        .build());

        if (!admin.isAdministrador()) {
            admin.setAdministrador(true);
        }

        operarioRepository.save(admin);
        log.info("Cuenta administradora '{}' comprobada", username);
    }
}
