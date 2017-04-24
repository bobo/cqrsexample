package se.cleancode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cleancode.AccountCreatedCommand;
import se.cleancode.AccountCreatedCommandHandler;
import se.cleancode.AccountCreatedEvent;

import java.util.UUID;


@RestController
@RequestMapping(value = "account", produces = "application/json")
public class AccountController {


    @Autowired
    AccountCreatedCommandHandler handler;

    @RequestMapping("create")
    public AccountCreatedEvent createAccount() {

        AccountCreatedCommand command = new AccountCreatedCommand(UUID.randomUUID().toString());
        AccountCreatedEvent event = handler.handle(command);
        return event;

    }

    @RequestMapping("create/{account-id}")
    public AccountCreatedEvent createAccount(@PathVariable("account-id") String accountId) {

        AccountCreatedCommand command = new AccountCreatedCommand(accountId);
        AccountCreatedEvent event = handler.handle(command);
        return event;

    }


}
