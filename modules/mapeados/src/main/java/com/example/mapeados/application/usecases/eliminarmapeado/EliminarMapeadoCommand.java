package com.example.mapeados.application.usecases.eliminarmapeado;

import com.example.mapeados.domain.aggregates.mapeado.valueobjects.CodeType;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdParty;

public record EliminarMapeadoCommand(
        String id
) {
}
