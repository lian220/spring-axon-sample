package web.api.axon.coreapi.Event;

import lombok.Getter;

@Getter
public class OrderCreatedEvent {
    private final String orderId;

    public OrderCreatedEvent(String orderId) {
        this.orderId = orderId;
    }
    
}
