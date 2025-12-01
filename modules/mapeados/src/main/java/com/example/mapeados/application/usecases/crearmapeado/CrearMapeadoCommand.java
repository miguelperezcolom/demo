package com.example.mapeados.application.usecases.crearmapeado;

import com.example.mapeados.domain.aggregates.mapeado.valueobjects.CodeType;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdParty;

public record CrearMapeadoCommand(
        ThirdParty tercero,
        CodeType tipo,
        String codigoRiu,
        String codigoTercero
) {
}
