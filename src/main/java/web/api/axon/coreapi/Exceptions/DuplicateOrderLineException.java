package web.api.axon.coreapi.Exceptions;

public class DuplicateOrderLineException extends IllegalStateException{

    public DuplicateOrderLineException(String productId) {
        super("Cannot duplicate order line for product identifier [" + productId + "]");
    }
    
}
