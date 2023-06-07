package web.api.axon.coreapi.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

@Getter
public class ConfirmOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;

    public ConfirmOrderCommand (String orderId) {
        this.orderId = orderId;
    }
    
}
