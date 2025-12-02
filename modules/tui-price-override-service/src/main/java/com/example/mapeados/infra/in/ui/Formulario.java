package com.example.mapeados.infra.in.ui;

import com.example.mapeados.domain.actors.ActorMapeado;
import io.mateu.uidl.annotations.Action;
import io.mateu.uidl.annotations.MateuUI;
import io.mateu.uidl.annotations.ReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@MateuUI("")
@Service
@RequiredArgsConstructor
public class Formulario {

    final ActorMapeado actor;

    String tercero = "TUI";

    String tipo = "HOTEL";

    String riuCode = "1";

    @ReadOnly
    String traduccion;

    @Action
    void traducir() {
        traduccion = actor.traducir(tercero, tipo, riuCode).orElse("NO ENCONTRADO");
    }

}
