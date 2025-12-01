package com.example.mapeados.domain.shared;

public abstract class AggregateRoot {

    private final UOW uow;


    protected AggregateRoot(UOW uow) {
        this.uow = uow;
    }

    public void publish(DomainEvent domainEvent) {
        uow.publish(domainEvent);
    }

}
