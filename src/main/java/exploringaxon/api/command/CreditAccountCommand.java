package exploringaxon.api.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Created by Dadepo Aderemi.
 */
public class CreditAccountCommand {

    @TargetAggregateIdentifier
    private final String account;

    private final Double amount;

    public CreditAccountCommand(String account, Double amount) {
        this.account = account;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public Double getAmount() {
        return amount;
    }
}
