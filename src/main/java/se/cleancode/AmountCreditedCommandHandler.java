package se.cleancode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AmountCreditedCommandHandler {

    @Autowired
    AccountRepository repository;

    public AmountCreditedEvent handle(AmountCreditedCommand command) {
        int currentVersion = repository.getCurrentVersion(command.accountId);
        verifyBalance(command);
        AmountCreditedEvent event = toEvent(command, currentVersion);
        repository.save(event);
        return event;
    }

    private AmountCreditedEvent toEvent(AmountCreditedCommand command, int currentVersion) {
        AmountCreditedEvent event = new AmountCreditedEvent();
        event.version = currentVersion+1;
        event.amount = command.amountCredited;
        event.accountId = command.accountId;
        event.eventId = UUID.randomUUID().toString();
        return event;
    }

    private void verifyBalance(AmountCreditedCommand command) {

    }
}
