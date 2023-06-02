package web.api.axon.commandModel;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import web.api.axon.coreapi.Command.ConfirmOrderCommand;
import web.api.axon.coreapi.Command.CreateOrderCommand;
import web.api.axon.coreapi.Command.ShipOrderCommand;
import web.api.axon.coreapi.Event.OrderConfirmedEvent;
import web.api.axon.coreapi.Event.OrderCreatedEvent;
import web.api.axon.coreapi.Event.OrderShippedEvent;
import web.api.axon.coreapi.Exceptions.UnconfirmedOrderException;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(command.getOrderId()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) throws UnconfirmedOrderException {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }
        AggregateLifecycle.apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) throws Exception {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }
        AggregateLifecycle.apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }

    protected OrderAggregate() {}

}
