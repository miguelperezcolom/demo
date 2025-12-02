package com.example.mapeados.infra.out.persistence.mapeado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public record LineaMapeado(
        String thirdParty,
        String type,
        String riuCode,
        String tpCode
) {
}
