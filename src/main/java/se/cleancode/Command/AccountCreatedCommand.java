package se.cleancode.Command;

import java.util.UUID;

public class AccountCreatedCommand {
    public String accountId;
    public String commandId;


    public AccountCreatedCommand(String accountId) {
        this.accountId = accountId;
        this.commandId = UUID.randomUUID().toString();
    }
}
