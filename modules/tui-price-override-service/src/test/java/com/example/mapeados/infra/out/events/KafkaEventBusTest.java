package com.example.mapeados.infra.out.events;

import com.example.mapeados.application.out.EventBus;
import com.example.mapeados.infra.out.events.events.MapeadoCreado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// Importa SOLO la configuración del Test Binder para reemplazar Kafka.
@Import(TestChannelBinderConfiguration.class)
class KafkaEventBusTest {

    @Autowired
    EventBus eventBus;


    @Test
    void envia() {

        eventBus.send(new MapeadoCreado("1")).block();

    }


}