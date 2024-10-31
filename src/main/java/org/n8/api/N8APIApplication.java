package org.n8.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Clase principal que configura y lanza la aplicación Spring Boot.
 * Utiliza MongoDB como base de datos, habilitando repositorios JPA de MongoDB en el paquete especificado.
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.n8.api.repository")
public class N8APIApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(N8APIApplication.class, args);
    }
}
