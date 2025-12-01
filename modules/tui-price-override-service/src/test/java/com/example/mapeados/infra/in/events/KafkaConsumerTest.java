package com.example.mapeados.infra.in.events;

import com.example.mapeados.infra.out.events.events.MapeadoCreado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para el consumidor de Kafka.
 *
 * SOLUCIÓN AL NULLPOINTEREXCEPTION:
 * Hemos reemplazado la inyección de InputDestination por la inyección directa del
 * bean SubscribableChannel. Esto evita que InputDestination.getChannelByName()
 * devuelva null y garantiza que estamos enviando el mensaje al canal correcto.
 */
@SpringBootTest
// Importa SOLO la configuración del Test Binder para reemplazar Kafka.
@Import(TestChannelBinderConfiguration.class)
class KafkaConsumerTest {

    // 1. Nombre del binding de entrada, tal como se define en application.yaml: <nombre-de-la-funcion>-in-0
    private final String INPUT_BINDING_NAME = "procesarMiTopico-in-0";

    // 2. Inyectamos el canal de Spring Integration (SubscribableChannel) directamente.
    // Usamos @Qualifier con el nombre del binding para inyectar el bean correcto.
    @Autowired
    @Qualifier(INPUT_BINDING_NAME)
    private SubscribableChannel inputChannel;


    @Test
    void debeConsumirYProcesarEvento() {
        // ARRANGE: Crear el evento (o el payload JSON)
        String idEvento = "123-abc-456";

        // Asegúrate de que el payload coincida con la estructura que espera tu deserializador JSON.
        String payloadJson = String.format("{\"id\":\"%s\", \"otroCampo\":\"valor\"}", idEvento);

        // Crea el objeto Message, es fundamental incluir el Content-Type.
        Message<byte[]> message = MessageBuilder
                .withPayload(payloadJson.getBytes())
                // Añade la cabecera Content-Type, crucial para el deserializador de Spring Cloud Stream
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();

        // ACT: Enviar el mensaje al canal de entrada simulado directamente.
        System.out.println("Enviando mensaje al canal: " + INPUT_BINDING_NAME);

        // Ahora usamos el canal inyectado (inputChannel) para enviar el mensaje.
        this.inputChannel.send(message);

        // ASSERT:
        // Aquí es donde deberías verificar el resultado observable de tu función.
        // Por ejemplo, si tu función guarda algo en MongoDB, aquí verificarías la BD usando un repository mockeado o real.

        // Ejemplo de verificación:
        // verify(miServicioMock).procesar(any(MapeadoCreado.class));
    }
}