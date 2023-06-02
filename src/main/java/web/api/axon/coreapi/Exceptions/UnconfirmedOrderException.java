package web.api.axon.coreapi.Exceptions;

public class UnconfirmedOrderException extends IllegalStateException {

    public UnconfirmedOrderException() {
        super("Cannot ship an order which has not been confirmed yet.");
    }
}
