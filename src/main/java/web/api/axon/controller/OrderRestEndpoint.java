package web.api.axon.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import web.api.axon.coreapi.Command.AddProductCommand;
import web.api.axon.coreapi.Command.ConfirmOrderCommand;
import web.api.axon.coreapi.Command.CreateOrderCommand;
import web.api.axon.coreapi.Command.ShipOrderCommand;
import web.api.axon.queryModel.OrderQueryService;
import web.api.axon.queryModel.OrderResponse;

@RestController
@RequiredArgsConstructor
public class OrderRestEndpoint {
    
    private final CommandGateway commandGateway;
    private final OrderQueryService orderQueryService;

    @PostMapping("/order")
    public CompletableFuture<String> createOrder() {
        return createOrder(UUID.randomUUID()
          .toString());
    }

    @PostMapping("/order/{order-id}")
    public CompletableFuture<String> createOrder(@PathVariable("order-id") String orderId) {
        return commandGateway.send(new CreateOrderCommand(orderId));
    }

    @PostMapping("/order/{order-id}/product/{product-id}")
    public CompletableFuture<Void> addProduct(@PathVariable("order-id") String orderId, @PathVariable("product-id") String productId) {
        return commandGateway.send(new AddProductCommand(orderId, productId));
    }

    @PostMapping("/ship-order")
    public CompletableFuture<Void> shipOrder() {
        String orderId = UUID.randomUUID().toString();
        return commandGateway.send(new CreateOrderCommand(orderId))
                .thenCompose(result -> commandGateway.send(new ConfirmOrderCommand(orderId)))
                .thenCompose(result -> commandGateway.send(new ShipOrderCommand(orderId)));
    }

    @GetMapping("/all-orders")
    public CompletableFuture<List<OrderResponse>> findAllOrders() {
        CompletableFuture<List<OrderResponse>> list = orderQueryService.findAllOrders();
        return list;
    }

    @GetMapping(path = "/all-orders-streaming", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderResponse> allOrdersStreaming() {
        return orderQueryService.allOrdersStreaming();
    }
}
