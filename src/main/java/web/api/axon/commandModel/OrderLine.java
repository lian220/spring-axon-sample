package web.api.axon.commandModel;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import lombok.Getter;
import web.api.axon.coreapi.Command.IncrementProductCountCommand;
import web.api.axon.coreapi.Command.ProductCountIncrementedEvent;
import web.api.axon.coreapi.Event.OrderConfirmedEvent;
import web.api.axon.coreapi.Exceptions.OrderAlreadyConfirmedException;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.Objects;

@Getter
public class OrderLine {
    
    @EntityId
    private final String productId;
    private Integer count;
    private boolean orderConfirmed;

    public OrderLine(String productId) {
        this.productId = productId;
        this.count = 1;
    }

    @CommandHandler
    public void handle(IncrementProductCountCommand command) {
        if(orderConfirmed) {
            throw new OrderAlreadyConfirmedException(command.getOrderId());
        }

        apply(new ProductCountIncrementedEvent(command.getOrderId(), productId));
    }

    @EventSourcingHandler
    public void on(ProductCountIncrementedEvent event) {
        this.count++;
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        this.orderConfirmed = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderLine orderLine = (OrderLine) o;
        return Objects.equals(productId, orderLine.productId) && Objects.equals(count, orderLine.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, count);
    }
}
