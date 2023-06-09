package web.api.axon.coreapi.Command;

import java.util.Objects;

import lombok.Getter;

@Getter
public class ProductCountIncrementedEvent {

    private final String orderId;
    private final String productId;

    public ProductCountIncrementedEvent(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCountIncrementedEvent that = (ProductCountIncrementedEvent) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

    @Override
    public String toString() {
        return "ProductCountIncrementedEvent{" + "orderId='" + orderId + '\'' + ", productId='" + productId + '\'' + '}';
    }
}
