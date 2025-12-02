package com.example.mapeados.application.usecases.eliminarmapeado;

import com.example.mapeados.application.out.EventBus;
import com.example.mapeados.application.out.MapeadoRepository;
import com.example.mapeados.domain.aggregates.mapeado.Mapeado;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.MapeadoId;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.RiuCode;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdPartyCode;
import com.example.mapeados.infra.out.events.events.MapeadoModificado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EliminarMapeadoUseCase {

    private final MapeadoRepository mapeadoRepository;
    private final EventBus eventBus;

    public Mono<?> run(EliminarMapeadoCommand command)
    {
        return mapeadoRepository
                .findById(new MapeadoId(command.id()))
                .flatMap(mapeado -> eventBus
                        .send(new MapeadoModificado(mapeado.id().id()))
                        .thenReturn(mapeado))
                .flatMap(mapeadoRepository::delete);
    }

}
