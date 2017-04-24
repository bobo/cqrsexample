package se.cleancode;

import java.util.UUID;

public class AmountCreditedCommand {
    public long amountCredited;
    public String accountId;
    public String commandId;

    public AmountCreditedCommand(String accountId, long amountCredited) {
        this.accountId = accountId;
        this.amountCredited = amountCredited;
        this.commandId = UUID.randomUUID().toString();

    }
}
