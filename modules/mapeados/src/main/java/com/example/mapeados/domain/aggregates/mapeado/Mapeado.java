package com.example.mapeados.domain.aggregates.mapeado;

import com.example.mapeados.domain.aggregates.mapeado.valueobjects.*;

public record Mapeado(MapeadoId id, ThirdParty thirdParty, CodeType codeType, RiuCode riuCode, ThirdPartyCode thirdPartyCode) {
}
