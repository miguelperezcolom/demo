package com.example.mapeados.infra.out.persistence.mapeado;

import com.example.mapeados.application.out.MapeadoRepository;
import com.example.mapeados.domain.aggregates.mapeado.LineaMapeado;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MongoMapeadoRepository implements MapeadoRepository {

    private final MapeadoEntityRepository repository;


    @Override
    public Mono<LineaMapeado> findById(MapeadoId id) {
        return repository.findById(id.id()).map(e -> new LineaMapeado(
                new MapeadoId(e.id),
                ThirdParty.TUI,
                CodeType.BOARD,
                new RiuCode(e.riuCode),
                new ThirdPartyCode(e.tpCode)
        ));
    }

    @Override
    public Mono<LineaMapeado> save(LineaMapeado mapeado) {
        return Mono.just(mapeado)
                .map(m -> new MapeadoEntity(
                        mapeado.id().id(),
                        mapeado.thirdParty().name(),
                        mapeado.codeType().name(),
                        mapeado.riuCode().value(),
                        mapeado.thirdPartyCode().value()
                ))
                .flatMap(repository::save)
                .map(e -> mapeado);
    }
}
