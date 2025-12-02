package com.example.mapeados.application.usecases.crearmapeado;

import com.example.mapeados.domain.actors.ActorMapeado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TraducirUseCase {

    private final ActorMapeado actorMapeado;

    public Mono<?> run(TraducirCommand command)
    {
        return Mono.just(actorMapeado.traducir(command.tercero(), command.tipo(), command.codigoRiu()));
    }

}
