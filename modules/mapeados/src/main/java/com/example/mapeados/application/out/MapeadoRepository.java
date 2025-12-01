package com.example.mapeados.application.out;

import com.example.mapeados.domain.aggregates.mapeado.Mapeado;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.MapeadoId;
import reactor.core.publisher.Mono;

public interface MapeadoRepository {
    Mono<Mapeado> findById(MapeadoId id);
    Mono<Mapeado> save(Mapeado mapeado);
}
