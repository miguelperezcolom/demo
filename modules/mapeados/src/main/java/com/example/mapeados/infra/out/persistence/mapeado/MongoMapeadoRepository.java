package com.example.mapeados.infra.out.persistence.mapeado;

import com.example.mapeados.application.out.MapeadoRepository;
import com.example.mapeados.domain.aggregates.mapeado.Mapeado;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoMapeadoRepository implements MapeadoRepository {

    private final MapeadoEntityRepository repository;


    @Override
    public Mono<Mapeado> findById(MapeadoId id) {
        return repository.findById(id.id()).map(e -> new Mapeado(
                new MapeadoId(e.id),
                ThirdParty.TUI,
                CodeType.BOARD,
                new RiuCode(e.riuCode),
                new ThirdPartyCode(e.tpCode)
        ));
    }

    @Override
    public Mono<Mapeado> save(Mapeado mapeado) {
        final String id = mapeado.id().id();

        return repository.findById(id)
                .flatMap(existingEntity -> {
                    existingEntity.setThirdParty(mapeado.thirdParty().name());
                    existingEntity.setType(mapeado.codeType().name());
                    existingEntity.setRiuCode(mapeado.riuCode().value());
                    existingEntity.setTpCode(mapeado.thirdPartyCode().value());
                    return repository.save(existingEntity);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    MapeadoEntity newEntity = new MapeadoEntity(
                            id,
                            mapeado.thirdParty().name(),
                            mapeado.codeType().name(),
                            mapeado.riuCode().value(),
                            mapeado.thirdPartyCode().value(),
                            0
                    );
                    return repository.save(newEntity);
                }))
                .map(e -> mapeado);
    }

    @Override
    public Mono<Void> delete(Mapeado mapeado) {
        return repository.findById(mapeado.id().id()).flatMap(repository::delete);
    }
}
