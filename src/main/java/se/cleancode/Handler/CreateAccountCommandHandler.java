package se.cleancode.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cleancode.Command.CreateAccountCommand;
import se.cleancode.Exception.AccountAlreadyExistsException;
import se.cleancode.Event.AccountCreatedEvent;
import se.cleancode.Repository.AccountRepository;
import se.cleancode.Messaging.MessageLog;

import java.util.UUID;

@Service
public class CreateAccountCommandHandler {

    @Autowired
    AccountRepository repository;

    @Autowired
    MessageLog messageLog;

    public AccountCreatedEvent handle(CreateAccountCommand command, long delay) {
        int currentVersion = repository.getCurrentVersion(command.accountId);
        verifyAccountNotExists(command.accountId, currentVersion);
        AccountCreatedEvent event = toEvent(command, currentVersion);
        repository.save(event);
        submitMessage(delay, event);
        return event;
    }

    public AccountCreatedEvent handle(CreateAccountCommand command) {
        return handle(command, 0);
    }

    private AccountCreatedEvent toEvent(CreateAccountCommand command, int currentVersion) {
        AccountCreatedEvent event = new AccountCreatedEvent();
        event.version = currentVersion + 1;
        event.eventId = UUID.randomUUID().toString();
        event.accountId = command.accountId;
        return event;
    }

    private void verifyAccountNotExists(String accountId, int currentVersion) {
        if (currentVersion != 0) {
            throw new AccountAlreadyExistsException("Account with id " + accountId + " already exists");
        }
    }

    private void submitMessage(long delay, AccountCreatedEvent event) {
        if (delay > 0) {
            messageLog.appendMessageWithDelay(event);
        } else {
            messageLog.appendMessage(event);
        }
    }

}
