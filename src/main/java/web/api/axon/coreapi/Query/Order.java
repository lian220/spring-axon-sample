package web.api.axon.coreapi.Query;

import lombok.Getter;

@Getter
public class Order {
    private final String orderId;
    private OrderStatus orderStatus;

    public Order(String orderId) {
        this.orderId = orderId;
        orderStatus = OrderStatus.CREATED;
    }

    public void setOrderConfirmed() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void setOrderShipped() {
        this.orderStatus = OrderStatus.SHIPPED;
    }

    // getters, equals/hashCode and toString functions
}

