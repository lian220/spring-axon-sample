package web.api.axon.queryModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import web.api.axon.coreapi.Query.FindAllOrderedProductsQuery;
import web.api.axon.coreapi.Query.Order;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final QueryGateway queryGateway;

    public CompletableFuture<List<OrderResponse>> findAllOrders() {
        return queryGateway.query(new FindAllOrderedProductsQuery(), ResponseTypes.multipleInstancesOf(Order.class))
          .thenApply(r -> r.stream()
            .map(OrderResponse::new)
            .collect(Collectors.toList()));
    }

    public Flux<OrderResponse> allOrdersStreaming() {
        Publisher<Order> publisher = queryGateway.streamingQuery(new FindAllOrderedProductsQuery(), Order.class);
        return Flux.from(publisher)
          .map(OrderResponse::new);
    }

    // public Integer totalShipped(String productId) {
    //     return queryGateway.scatterGather(new TotalProductsShippedQuery(productId), ResponseTypes.instanceOf(Integer.class), 10L, TimeUnit.SECONDS)
    //       .reduce(0, Integer::sum);
    // }

    // public Flux<OrderResponse> orderUpdates(String orderId) {
    //     return subscriptionQuery(new OrderUpdatesQuery(orderId), ResponseTypes.instanceOf(Order.class)).map(OrderResponse::new);
    // }

    // private <Q, R> Flux<R> subscriptionQuery(Q query, ResponseType<R> resultType) {
    //     SubscriptionQueryResult<R, R> result = queryGateway.subscriptionQuery(query, resultType, resultType);
    //     return result.initialResult()
    //       .concatWith(result.updates())
    //       .doFinally(signal -> result.close());
    // }
}
