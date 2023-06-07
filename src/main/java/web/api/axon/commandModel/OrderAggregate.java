package web.api.axon.commandModel;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import web.api.axon.coreapi.Command.AddProductCommand;
import web.api.axon.coreapi.Command.ConfirmOrderCommand;
import web.api.axon.coreapi.Command.CreateOrderCommand;
import web.api.axon.coreapi.Command.ShipOrderCommand;
import web.api.axon.coreapi.Event.OrderConfirmedEvent;
import web.api.axon.coreapi.Event.OrderCreatedEvent;
import web.api.axon.coreapi.Event.OrderShippedEvent;
import web.api.axon.coreapi.Event.ProductAddedEvent;
import web.api.axon.coreapi.Exceptions.DuplicateOrderLineException;
import web.api.axon.coreapi.Exceptions.OrderAlreadyConfirmedException;
import web.api.axon.coreapi.Exceptions.UnconfirmedOrderException;

@Aggregate(snapshotTriggerDefinition = "orderAggregateSnapshotTriggerDefinition")
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @AggregateMember
    private Map<String, OrderLine> orderLines;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(AddProductCommand command) {
        if (orderConfirmed) {
            throw new OrderAlreadyConfirmedException(orderId);
        }

        String productId = command.getProductId();
        if (orderLines.containsKey(productId)) {
            throw new DuplicateOrderLineException(productId);
        }
        apply(new ProductAddedEvent(orderId, productId));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) throws UnconfirmedOrderException {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }
        apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) throws Exception {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }
        apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
        this.orderLines = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }

    protected OrderAggregate() {}

}
