package web.api.axon.queryModel;

import java.util.Map;

import web.api.axon.coreapi.Query.Order;

import static web.api.axon.queryModel.OrderStatusResponse.toResponse;

public class OrderResponse {

    private String orderId;
    private Map<String, Integer> products;
    private OrderStatusResponse orderStatus;

    OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.products = order.getProducts();
        this.orderStatus = toResponse(order.getOrderStatus());
    }

    /**
     * Added for the integration test, since it's using Jackson for the response
     */
    OrderResponse() {
    }

    public String getOrderId() {
        return orderId;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public OrderStatusResponse getOrderStatus() {
        return orderStatus;
    }
}

