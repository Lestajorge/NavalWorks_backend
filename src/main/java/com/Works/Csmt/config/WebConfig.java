package com.Works.Csmt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configura recursos estáticos que Spring MVC puede servir directamente.
 *
 * En esta clase se publica el contenido del directorio de archivos subidos
 * mediante direcciones que comienzan por /uploads/.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Conecta las URL /uploads/** con los archivos del directorio uploads/.
     *
     * Así, una petición a una ruta como /uploads/archivo.pdf busca ese archivo
     * en el directorio de subidas de la aplicación.
     *
     * @param registry registro donde se declaran las rutas de recursos estáticos
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
