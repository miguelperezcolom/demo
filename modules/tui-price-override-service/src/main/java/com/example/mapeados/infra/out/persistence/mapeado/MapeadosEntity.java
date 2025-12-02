package com.example.mapeados.infra.out.persistence.mapeado;

import com.example.mapeados.domain.actors.ActualizarMapeadoCommand;
import com.example.shared.infra.events.MapeadoCreado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "mapeados")
@AllArgsConstructor@NoArgsConstructor
@Getter
public class MapeadosEntity {

    @Id
    String id;
    List<LineaMapeado> lineas = new ArrayList<>();

    public MapeadosEntity aplicar(MapeadoCreado evento) {
        if (lineas.stream()
                .anyMatch(linea -> linea.thirdParty().equals(evento.thirdParty()))) {
            lineas = lineas.stream()
                    .map(linea -> matches(linea, evento)?new LineaMapeado(
                            evento.thirdParty(),
                            evento.type(),
                            evento.riuCode(),
                            evento.tpCode()
                    ):linea)
                    .toList();
        }
        lineas.add(new LineaMapeado(
                evento.thirdParty(),
                evento.type(),
                evento.riuCode(),
                evento.tpCode()
        ));
        return this;
    }

    private boolean matches(LineaMapeado linea, MapeadoCreado mapeadoCreado) {
        return linea.thirdParty().equals(mapeadoCreado.thirdParty())
                && linea.type().equals(mapeadoCreado.type())
                && linea.riuCode().equals(mapeadoCreado.riuCode());
    }
}
