package se.cleancode.Command;

public class AmountDebitedCommand {
    public String accountId;
    public long amount;
    public String commandId;

    public AmountDebitedCommand(String accountId, long amount) {

        this.accountId = accountId;
        this.amount = amount;
    }
}
