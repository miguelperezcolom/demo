package com.example.mapeados.infra.in.ui.pages;

import com.example.mapeados.application.out.MapeadoRepository;
import com.example.mapeados.application.usecases.grabarmapeado.GrabarMapeadoCommand;
import com.example.mapeados.application.usecases.grabarmapeado.GrabarMapeadoUseCase;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.CodeType;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.MapeadoId;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdParty;
import io.mateu.uidl.annotations.Ignored;
import io.mateu.uidl.annotations.MainAction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FormularioEditarMapeado {

    final GrabarMapeadoUseCase grabarMapeadoUseCase;
    final ApplicationContext applicationContext;
    private final MapeadoRepository mapeadoRepository;
    @Ignored
    String id;

    @NotNull
    ThirdParty thirdParty;

    @NotNull
    CodeType codeType;

    @NotEmpty
    String valorRiu;

    @NotEmpty
    String valorExterno;

    @MainAction
    Mono<Mapeados> save() {
        return grabarMapeadoUseCase.run(new GrabarMapeadoCommand(
                id,
                thirdParty,
                codeType,
                valorRiu,
                valorExterno
                ))
                .thenReturn(applicationContext.getBean(Mapeados.class));
    }

    public Mono<FormularioEditarMapeado> load(String idMapeado) {
        return mapeadoRepository.findById(new MapeadoId(idMapeado))
                .doOnNext(m -> {
                    id = m.id().id();
                    thirdParty = m.thirdParty();
                    codeType = m.codeType();
                    valorRiu = m.riuCode().value();
                    valorExterno = m.thirdPartyCode().value();
                })
                .thenReturn(this);
    }

}
