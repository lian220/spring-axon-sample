package web.api.axon.coreapi.Event;

import lombok.Getter;

@Getter
public class OrderConfirmedEvent {

    private final String orderId;

    public OrderConfirmedEvent (String orderId) {
        this.orderId = orderId;
    }
    
}
