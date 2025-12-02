package com.example.mapeados.infra.out.persistence.mapeado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("mapeado")
@AllArgsConstructor@NoArgsConstructor
@Getter
public class MapeadoEntity {

    @Id
    String id;
    String thirdParty;
    String type;
    String riuCode;
    String tpCode;

}
