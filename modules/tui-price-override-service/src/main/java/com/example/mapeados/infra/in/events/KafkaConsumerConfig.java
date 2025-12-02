package com.example.mapeados.infra.in.events;

import com.example.mapeados.domain.actors.ActorMapeado;
import com.example.mapeados.domain.actors.ActualizarMapeadoCommand;
import com.example.mapeados.infra.out.persistence.mapeado.MapeadoEntityRepository;
import com.example.shared.infra.events.MapeadoCreado;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    final ActorMapeado actorMapeado;
    final MapeadoEntityRepository mapeadoEntityRepository;

    // 1. Define un @Bean que sea de tipo java.util.function.Consumer<MiEvento>
    //    Si trabajas con WebFlux/Reactor, puedes usar un Consumer<Flux<MiEvento>>
//
//    @Bean
//    public Consumer<MapeadoCreado> procesarMiTopico() {
//        return evento -> {
//            // 2. Aquí está tu lógica de negocio
//            System.out.println("✅ Evento Consumido | ID: " + evento.id() + ", Data: " + "xxx");
//
//            // Lógica para guardar en DB, llamar a otro servicio, etc.
//            // Nota: Esta parte es síncrona/imperativa, pero es llamada
//            // de forma reactiva por el framework.
//        };
//    }

    // Opcional: Si quieres un procesamiento completamente reactivo

    @Bean
    public Consumer<Flux<MapeadoCreado>> procesarMiTopicoReactivo() {
        return eventos -> eventos
                //todo: mover a otro consumidor
                .doOnNext(evento -> actorMapeado.send(new ActualizarMapeadoCommand(
                        evento.thirdParty(),
                        evento.type(),
                        evento.riuCode(),
                        evento.tpCode()
                )))
                .doOnNext(evento -> mapeadoEntityRepository
                        .findAll()
                        .map(mapeado -> mapeado.aplicar(evento))
                        .doOnNext(mapeadoEntityRepository::save))
                .subscribe();
//        return eventos -> eventos
//            .doOnNext(evento -> {
//                System.out.println("✅ Evento Consumido Reactivo | ID: " + evento.id());
//                // Lógica reactiva (e.g., Mono.just(evento).flatMap(repo::save))
//            })
//            .subscribe(); // Iniciar el flujo reactivo
    }
}
