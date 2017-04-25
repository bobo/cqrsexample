package se.cleancode.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cleancode.Command.AmountDebitedCommand;
import se.cleancode.Event.AmountCreditedEvent;
import se.cleancode.Event.AmountDebitedEvent;
import se.cleancode.Event.Event;
import se.cleancode.Exception.InsufficientBalanceException;
import se.cleancode.Exception.NoSuchAccountException;
import se.cleancode.Messaging.MessageLog;
import se.cleancode.Repository.AccountRepository;

import java.util.List;
import java.util.UUID;

import static se.cleancode.Controller.FailureUtil.sleep;

@Service
public class AmountDebitedCommandHandler {

    @Autowired
    AccountRepository repository;

    @Autowired
    MessageLog messageLog;

    public AmountDebitedEvent handle(AmountDebitedCommand command, long delay) {
        int currentVersion = repository.getCurrentVersion(command.accountId);
        verifyAccountExists(currentVersion);
        verifyBalance(command);
        sleep(delay);
        AmountDebitedEvent event = toEvent(command, currentVersion);
        repository.save(event);
        messageLog.appendMessage(event);
        return event;
    }


    public AmountDebitedEvent handle(AmountDebitedCommand command) {
        return handle(command,0);
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
