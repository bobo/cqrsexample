package se.cleancode.Command;

import java.util.UUID;

public class CreateAccountCommand {
    public String accountId;
    public String commandId;


    public CreateAccountCommand(String accountId) {
        this.accountId = accountId;
        this.commandId = UUID.randomUUID().toString();
    }
}
