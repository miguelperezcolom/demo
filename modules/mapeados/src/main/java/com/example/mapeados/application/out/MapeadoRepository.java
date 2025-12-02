package com.example.mapeados.application.out;

import com.example.mapeados.domain.aggregates.mapeado.Mapeado;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.MapeadoId;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MapeadoRepository {
    Mono<Mapeado> findById(MapeadoId id);
    Mono<Mapeado> save(Mapeado mapeado);
    Mono<Void> delete(Mapeado mapeado);
}
