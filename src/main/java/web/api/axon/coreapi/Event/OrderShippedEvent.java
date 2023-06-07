package web.api.axon.coreapi.Event;

import lombok.Getter;

@Getter
public class OrderShippedEvent {
    private final String orderId;

    public OrderShippedEvent (String orderId) {
        this.orderId = orderId;
    }
    
}
