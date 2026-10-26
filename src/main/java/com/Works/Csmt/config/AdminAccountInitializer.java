package com.Works.Csmt.config;

import F110.model.Operario;
import F110.repository.OperarioRepository;
import com.navantia.f110.enums.model.Especialista;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
                        .especialista(Especialista.TUBERO)
                        .administrador(true)
                        .build());

        if (!admin.isAdministrador()) {
            admin.setAdministrador(true);
        }

        operarioRepository.save(admin);
        log.info("Cuenta administradora '{}' comprobada", username);
    }
}
