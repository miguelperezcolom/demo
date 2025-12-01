package com.example.mapeados.application.usecases.grabarmapeado;

import com.example.mapeados.application.out.EventBus;
import com.example.mapeados.application.out.MapeadoRepository;
import com.example.mapeados.domain.aggregates.mapeado.LineaMapeado;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.MapeadoId;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.RiuCode;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdPartyCode;
import com.example.mapeados.infra.out.events.events.MapeadoModificado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GrabarMapeadoUseCase {

    private final MapeadoRepository mapeadoRepository;
    private final EventBus eventBus;

    public Mono<?> run(GrabarMapeadoCommand command)
    {
        return mapeadoRepository.save(new LineaMapeado(
                new MapeadoId(command.id()),
                command.tercero(),
                command.tipo(),
                new RiuCode(command.codigoRiu()),
                new ThirdPartyCode(command.codigoTercero())
        ))
                .flatMap(mapeado -> eventBus
                        .send(new MapeadoModificado(mapeado.id().id())));
    }

}
