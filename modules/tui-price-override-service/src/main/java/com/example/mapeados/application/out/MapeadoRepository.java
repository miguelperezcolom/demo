package com.example.mapeados.application.out;

import com.example.mapeados.domain.aggregates.mapeado.LineaMapeado;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.MapeadoId;
import reactor.core.publisher.Mono;

public interface MapeadoRepository {
    Mono<LineaMapeado> findById(MapeadoId id);
    Mono<LineaMapeado> save(LineaMapeado mapeado);
}
