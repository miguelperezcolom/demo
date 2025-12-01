package com.example.mapeados.application.usecases.grabarmapeado;

import com.example.mapeados.domain.aggregates.mapeado.valueobjects.CodeType;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdParty;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdPartyCode;

public record GrabarMapeadoCommand(
        String id,
        ThirdParty tercero,
        CodeType tipo,
        String codigoRiu,
        String codigoTercero
) {
}
