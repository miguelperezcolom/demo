package com.example.mapeados.infra.out.persistence.mapeado;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MapeadoEntityRepository extends ReactiveMongoRepository<MapeadoEntity, String> {
}
