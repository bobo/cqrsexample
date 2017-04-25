package se.cleancode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static se.cleancode.FailureUtil.*;

@Service
public class AmountCreditedCommandHandler {

    @Autowired
    AccountRepository repository;

    public AmountCreditedEvent handle(AmountCreditedCommand command, long delay) {
        int currentVersion = repository.getCurrentVersion(command.accountId);
        verifyBalance(command);
        sleep(delay);
        AmountCreditedEvent event = toEvent(command, currentVersion);
        repository.save(event);
        return event;
    }

    public AmountCreditedEvent handle(AmountCreditedCommand command) {
        return handle(command, 0);
    }

    private AmountCreditedEvent toEvent(AmountCreditedCommand command, int currentVersion) {
        AmountCreditedEvent event = new AmountCreditedEvent();
        event.version = currentVersion + 1;
        event.amount = command.amountCredited;
        event.accountId = command.accountId;
        event.eventId = UUID.randomUUID().toString();
        return event;
    }

    private void verifyBalance(AmountCreditedCommand command) {

    }
}
