package com.example.mapeados.domain.actors;

import com.example.mapeados.domain.shared.Actor;
import com.example.mapeados.domain.shared.ActorCommand;
import com.example.mapeados.infra.out.persistence.mapeado.LineaMapeado;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorMapeado extends Actor {

    //todo: sustituir por mapa y mapa reverse
    List<LineaMapeado> lineas = new ArrayList<>();

    public Optional<String> traducir(String thirdParty, String type, String riuCode) {
        return lineas.stream()
                .filter(linea -> matches(linea, thirdParty, type, riuCode)).
                findAny()
                .map(LineaMapeado::tpCode);
    }

    @Override
    protected void apply(ActorCommand command) {
        if (command instanceof ActualizarMapeadoCommand actualizarMapeadoCommand) {
            if (lineas.stream()
                    .anyMatch(linea -> linea.thirdParty().equals(actualizarMapeadoCommand.thirdParty()))) {
                lineas = lineas.stream()
                        .map(linea -> matches(linea, actualizarMapeadoCommand)?new LineaMapeado(
                                actualizarMapeadoCommand.thirdParty(),
                                actualizarMapeadoCommand.type(),
                                actualizarMapeadoCommand.riuCode(),
                                actualizarMapeadoCommand.tpCode()
                        ):linea)
                        .toList();
            }
            lineas.add(new LineaMapeado(
                    actualizarMapeadoCommand.thirdParty(),
                    actualizarMapeadoCommand.type(),
                    actualizarMapeadoCommand.riuCode(),
                    actualizarMapeadoCommand.tpCode()
            ));
        }
    }

    private boolean matches(LineaMapeado linea, ActualizarMapeadoCommand actualizarMapeadoCommand) {
        return linea.thirdParty().equals(actualizarMapeadoCommand.thirdParty())
                && linea.type().equals(actualizarMapeadoCommand.type())
                && linea.riuCode().equals(actualizarMapeadoCommand.riuCode());
    }

    private boolean matches(LineaMapeado linea,
                            String thirdParty,
                            String type,
                            String riuCode
    ) {
        return linea.thirdParty().equals(thirdParty)
                && linea.type().equals(type)
                && linea.riuCode().equals(riuCode);
    }
}
