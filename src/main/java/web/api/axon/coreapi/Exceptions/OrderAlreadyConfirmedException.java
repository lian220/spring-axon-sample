package web.api.axon.coreapi.Exceptions;

public class OrderAlreadyConfirmedException extends IllegalStateException {

    public OrderAlreadyConfirmedException(String orderId) {
        super("Cannot perform operation because order [" + orderId + "] is already confirmed.");
    }
}
