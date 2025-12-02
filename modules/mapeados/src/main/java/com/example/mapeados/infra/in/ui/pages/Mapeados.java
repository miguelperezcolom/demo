package com.example.mapeados.infra.in.ui.pages;

import com.example.mapeados.application.usecases.eliminarmapeado.EliminarMapeadoCommand;
import com.example.mapeados.application.usecases.eliminarmapeado.EliminarMapeadoUseCase;
import com.example.mapeados.domain.aggregates.mapeado.valueobjects.ThirdParty;
import com.example.mapeados.infra.out.persistence.mapeado.MapeadoEntityRepository;
import io.mateu.uidl.annotations.Action;
import io.mateu.uidl.interfaces.Crud;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

record Filters() {

}

record Row(String id, String thirdParty, String type, String riu, String external) {

}

@Service
@RequiredArgsConstructor
public class Mapeados implements Crud<Filters, Row> {

    final FormularioCrearMapeado formularioCrearMapeado;
    final FormularioEditarMapeado formularioEditarMapeado;
    final EliminarMapeadoUseCase eliminarMapeadoUseCase;
    final MapeadoEntityRepository mapeadoEntityRepository;

    @Override
    public Mono<Page<Row>> fetchRows(String searchText, Filters filters, Pageable pageable) throws Throwable {
        return mapeadoEntityRepository.findAll()
                .map(e -> new Row(
                        e.getId(),
                        ThirdParty.TUI.name(),
                        e.getType(),
                        e.getRiuCode(),
                        e.getTpCode()
                ))
                .collectList()
                .map(list -> (Page) new PageImpl<>(list, pageable, list.size()));
    }

    @Action
    FormularioCrearMapeado crear() {
        return formularioCrearMapeado;
    }

    @Override
    public void delete(List<Row> selection) throws Throwable {
        selection.stream()
                .map(Row::id)
                .forEach(this::eliminar);
    }

    @SneakyThrows
    private Object eliminar(String id) {
        return eliminarMapeadoUseCase.run(new EliminarMapeadoCommand(id))
                .toFuture().get();
    }

    @Override
    public Object getDetail(Row row) throws Throwable {
        return formularioEditarMapeado.load(row.id());
    }
}
