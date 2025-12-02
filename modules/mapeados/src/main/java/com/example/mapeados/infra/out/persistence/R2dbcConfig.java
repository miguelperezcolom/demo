package com.example.mapeados.infra.out.persistence;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class R2dbcConfig {

    /**
     * Bean para inicializar la base de datos MariaDB con schema.sql
     */
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        // Carga el archivo schema.sql desde resources/
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql")
        );

        // Si tienes también data.sql, añádelo así:
        // populator.addScript(new ClassPathResource("data.sql"));

        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}