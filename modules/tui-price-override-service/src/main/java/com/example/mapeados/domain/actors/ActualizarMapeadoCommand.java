package com.example.mapeados.domain.actors;

import com.example.mapeados.domain.shared.ActorCommand;

public record ActualizarMapeadoCommand(String thirdParty,
                                       String type,
                                       String riuCode,
                                       String tpCode) implements ActorCommand {
}
