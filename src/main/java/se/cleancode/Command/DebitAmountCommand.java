package se.cleancode.Command;

public class DebitAmountCommand {
    public String accountId;
    public long amount;
    public String commandId;

    public DebitAmountCommand(String accountId, long amount) {

        this.accountId = accountId;
        this.amount = amount;
    }
}
