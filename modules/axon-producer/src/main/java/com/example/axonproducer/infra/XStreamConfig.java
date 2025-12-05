package com.example.axonproducer.infra;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class XStreamConfig {

    @Bean
    @Primary
    public XStreamSerializer xStreamSerializer() {
        XStream xStream = new XStream();

        // 1. CONFIGURAR SEGURIDAD
        xStream.addPermission(AnyTypePermission.ANY);

        // 2. AÑADIR A LA LISTA BLANCA TUS PAQUETES DE MENSAJES
        // Esto permite serializar todas las clases bajo estos paquetes
        xStream.allowTypesByWildcard(new String[] {
                "com.example.shared.events.**",
                "com.example.shared.commands.**",
                "com.example.axonproducer.domain.events.**",
                // Asegúrate de añadir cualquier otro paquete de eventos/comandos/objetos de valor que serialices
        });

        // O permitir clases individuales:
        // xStream.allowTypes(new Class[] { OrderCreatedEvent.class, CommandExecuted.class });

        // 3. Crear el Serializer de Axon con la configuración segura
        return XStreamSerializer.builder().xStream(xStream).build();
    }
}