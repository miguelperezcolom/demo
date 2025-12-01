package com.example.mapeados.infra.in.events;

import com.example.mapeados.infra.out.events.events.MapeadoCreado;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumerConfig {

    // 1. Define un @Bean que sea de tipo java.util.function.Consumer<MiEvento>
    //    Si trabajas con WebFlux/Reactor, puedes usar un Consumer<Flux<MiEvento>>
    @Bean
    public Consumer<MapeadoCreado> procesarMiTopico() {
        return evento -> {
            // 2. Aquí está tu lógica de negocio
            System.out.println("✅ Evento Consumido | ID: " + evento.id() + ", Data: " + "xxx");

            // Lógica para guardar en DB, llamar a otro servicio, etc.
            // Nota: Esta parte es síncrona/imperativa, pero es llamada
            // de forma reactiva por el framework.
        };
    }

    // Opcional: Si quieres un procesamiento completamente reactivo
    /*
    @Bean
    public Consumer<Flux<MiEvento>> procesarMiTopicoReactivo() {
        return eventos -> eventos
            .doOnNext(evento -> {
                System.out.println("✅ Evento Consumido Reactivo | ID: " + evento.id());
                // Lógica reactiva (e.g., Mono.just(evento).flatMap(repo::save))
            })
            .subscribe(); // Iniciar el flujo reactivo
    }
    */
}
