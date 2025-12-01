package com.example.mapeados.application.usecases.grabarmapeado;

import com.example.mapeados.infra.out.persistence.mapeado.MapeadoEntity;
import com.example.mapeados.infra.out.persistence.mapeado.MapeadoEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrearMapeadoUseCaseTest {

    @Autowired
    MapeadoEntityRepository mapeadoEntityRepository;

    @BeforeEach
    void setUp() {
        mapeadoEntityRepository.deleteAll().block();
    }

    @AfterEach
    void tearDown() {
        mapeadoEntityRepository.deleteAll().block();
    }

    @Test
    void graba() {
        var e = new MapeadoEntity("1", "tipo", "riu", "tercero");
        mapeadoEntityRepository.save(e).block();
        var count = mapeadoEntityRepository.findAll().count().block();
        assertEquals(1, count);
    }

}