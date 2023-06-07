package web.api.axon.coreapi.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

@Getter
public class ShipOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;

    public ShipOrderCommand (String orderId) {
        this.orderId = orderId;
    }
    
}
