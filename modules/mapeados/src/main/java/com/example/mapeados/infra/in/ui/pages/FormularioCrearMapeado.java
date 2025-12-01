package com.example.mapeados.infra.in.ui.pages;

import com.example.mapeados.application.usecases.crearmapeado.CrearMapeadoCommand;
import com.example.mapeados.application.usecases.crearmapeado.CrearMapeadoUseCase;
import com.example.mapeados.application.usecases.grabarmapeado.GrabarMapeadoCommand;
import com.example.mapeados.application.usecases.grabarmapeado.GrabarMapeadoUseCase;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.CodeType;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdParty;
import io.mateu.uidl.annotations.MainAction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FormularioCrearMapeado {

    final CrearMapeadoUseCase grabarMapeadoUseCase;
    final ApplicationContext applicationContext;

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
        return grabarMapeadoUseCase.run(new CrearMapeadoCommand(
                thirdParty,
                codeType,
                valorRiu,
                valorExterno
                ))
                .thenReturn(applicationContext.getBean(Mapeados.class));
    }

}
