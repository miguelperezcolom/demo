package com.example.mapeados.domain.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Actor {

    List<ActorCommand> inbox =  Collections.synchronizedList(new ArrayList<>());

    public void send(ActorCommand command) {
        inbox.add(command);
    }

    protected abstract void apply(ActorCommand command);

}
