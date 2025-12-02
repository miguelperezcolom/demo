package com.example.mapeados.infra.out.persistence.mapeado;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MapeadoEntityRepository extends ReactiveCrudRepository<MapeadoEntity, String> {
}
