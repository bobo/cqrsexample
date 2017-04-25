package se.cleancode.Command;

import java.util.UUID;

public class CreditAmountCommand {
    public long amountCredited;
    public String accountId;
    public String commandId;

    public CreditAmountCommand(String accountId, long amountCredited) {
        this.accountId = accountId;
        this.amountCredited = amountCredited;
        this.commandId = UUID.randomUUID().toString();

    }
}
