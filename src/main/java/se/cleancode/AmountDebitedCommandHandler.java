package se.cleancode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AmountDebitedCommandHandler {

    @Autowired
    AccountRepository repository;

    public AmountDebitedEvent handle(AmountDebitedCommand command) {
        int currentVersion = repository.getCurrentVersion(command.accountId);
        verifyAccountExists(currentVersion);
        verifyBalance(command);
        AmountDebitedEvent event = toEvent(command, currentVersion);
        repository.save(event);
        return event;

    }

    private void verifyAccountExists(int currentVersion) {
        if(currentVersion==0){
            throw new NoSuchAccountException();
        }
    }

    private AmountDebitedEvent toEvent(AmountDebitedCommand command, int currentVersion) {
        AmountDebitedEvent event = new AmountDebitedEvent();
        event.version = currentVersion + 1;
        event.amount = command.amount;
        event.accountId = command.accountId;
        event.eventId = UUID.randomUUID().toString();
        return event;
    }

    private void verifyBalance(AmountDebitedCommand command) {
        List<Event> events = repository.get(command.accountId);
        int currentBalance = getCurrentBalance(events);
        if(currentBalance<command.amount) {
            throw new InsufficientBalanceException();
        }
    }

    private int getCurrentBalance(List<Event> events) {
        int currentBalance = 0;
        for(Event e : events){
            if (e instanceof AmountDebitedEvent) {
                currentBalance -= ((AmountDebitedEvent) e).amount;
            } else if (e instanceof AmountCreditedEvent) {
                currentBalance += ((AmountCreditedEvent) e).amount;
            }
        } return currentBalance;
    }
}
