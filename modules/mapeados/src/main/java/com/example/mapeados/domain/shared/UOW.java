package com.example.mapeados.domain.shared;

import java.util.ArrayList;
import java.util.List;

public class UOW {

    private final List<DomainEvent> events = new ArrayList<>();

    public void publish(DomainEvent domainEvent) {
        events.add(domainEvent);
    }

    public List<DomainEvent> getEvents() {
        var out = List.copyOf(events);
        events.clear();
        return out;
    }


}
