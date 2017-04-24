package se.cleancode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountCreatedCommandHandler {

    @Autowired
    AccountRepository repository;

    public AccountCreatedEvent handle(AccountCreatedCommand command) {
        int currentVersion = repository.getCurrentVersion(command.accountId);
        verifyAccountNotExists(command.accountId,currentVersion);
        AccountCreatedEvent event = toEvent(command, currentVersion);
        repository.save(event);
        return event;
    }

    private AccountCreatedEvent toEvent(AccountCreatedCommand command, int currentVersion) {
        AccountCreatedEvent event = new AccountCreatedEvent();
        event.version = currentVersion+1;
        event.eventId = UUID.randomUUID().toString();
        event.accountId = command.accountId;
        return event;
    }

    private void verifyAccountNotExists(String accountId, int currentVersion) {
        if(currentVersion != 0) {
            throw new AccountAlreadyExistsException("Account with id "+ accountId +" already exists");
        }
    }
}