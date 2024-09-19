package org.n8.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.n8.api.repository")
public class N8APIApplication {
    public static void main(String[] args) {
        SpringApplication.run(N8APIApplication.class, args);
    }
}

