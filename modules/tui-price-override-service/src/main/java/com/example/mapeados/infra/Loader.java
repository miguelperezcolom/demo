package com.example.mapeados.infra;

import com.example.mapeados.domain.actors.ActorMapeado;
import com.example.mapeados.domain.actors.ActualizarMapeadoCommand;
import com.example.mapeados.infra.out.persistence.mapeado.MapeadoEntityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Loader {

    final ActorMapeado actorMapeado;
    final MapeadoEntityRepository mapeadoEntityRepository;

    @PostConstruct
    void loadActors() {
        mapeadoEntityRepository.findAll()
                .doOnNext(mapeado -> {
                    mapeado.getLineas()
                            .forEach(linea -> actorMapeado
                                    .send(new ActualizarMapeadoCommand(
                                            linea.thirdParty(),
                                            linea.type(),
                                            linea.riuCode(),
                                            linea.tpCode())
                    ));
                })
                .blockLast();
    }

}
