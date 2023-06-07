package web.api.axon.queryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import web.api.axon.coreapi.Event.OrderCreatedEvent;
import web.api.axon.coreapi.Query.FindAllOrderedProductsQuery;
import web.api.axon.coreapi.Query.Order;

@Service
public class OrdersEventHandler {
    
    private final Map<String, Order> orders = new HashMap<>();

    @EventHandler
    public void on(OrderCreatedEvent event) {
        String orderId = event.getOrderId();
        orders.put(orderId, new Order(orderId));
    }

    @QueryHandler
    public List<Order> handle(FindAllOrderedProductsQuery query) {
        return new ArrayList<>(orders.values());
    }
}
