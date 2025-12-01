package com.example.mapeados.application.usecases.crearmapeado;

import com.example.mapeados.application.out.EventBus;
import com.example.mapeados.application.out.MapeadoRepository;
import com.example.mapeados.domain.aggregates.mapeado.Mapeado;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.MapeadoId;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.RiuCode;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdPartyCode;
import com.example.mapeados.infra.out.events.events.MapeadoCreado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.mapeados.domain.shared.UUIDFactory.createUUID;

@Service
@RequiredArgsConstructor
public class CrearMapeadoUseCase {

    private final MapeadoRepository mapeadoRepository;
    private final EventBus eventBus;

    public Mono<?> run(CrearMapeadoCommand command)
    {
        return mapeadoRepository.save(new Mapeado(
                new MapeadoId(createUUID().toString()),
                command.tercero(),
                command.tipo(),
                new RiuCode(command.codigoRiu()),
                new ThirdPartyCode(command.codigoTercero())
        ))
                .flatMap(mapeado -> eventBus
                        .send(new MapeadoCreado(mapeado.id().id())));
    }

}
