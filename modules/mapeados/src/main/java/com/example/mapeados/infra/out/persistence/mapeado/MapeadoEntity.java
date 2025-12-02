package com.example.mapeados.infra.out.persistence.mapeado;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("mapeado")
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter
@With
public class MapeadoEntity {

    @Id
    String id;
    String thirdParty;
    String type;
    String riuCode;
    String tpCode;
    @Version
    int version;

}
