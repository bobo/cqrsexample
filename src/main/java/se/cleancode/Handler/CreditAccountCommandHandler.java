package se.cleancode.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cleancode.Command.CreditAmountCommand;
import se.cleancode.Repository.AccountRepository;
import se.cleancode.Event.AmountCreditedEvent;
import se.cleancode.Messaging.MessageLog;

import java.util.UUID;

import static se.cleancode.Controller.FailureUtil.*;

@Service
public class CreditAccountCommandHandler {

    @Autowired
    AccountRepository repository;

    @Autowired
    MessageLog messageLog;

    public AmountCreditedEvent handle(CreditAmountCommand command, long delay) {
        int currentVersion = repository.getCurrentVersion(command.accountId);
        verifyBalance(command);
        computeThings(delay);
        AmountCreditedEvent event = toEvent(command, currentVersion);
        repository.save(event);
        messageLog.appendMessage(event);
        return event;
    }



    public AmountCreditedEvent handle(CreditAmountCommand command) {
        return handle(command, 0);
    }

    private AmountCreditedEvent toEvent(CreditAmountCommand command, int currentVersion) {
        AmountCreditedEvent event = new AmountCreditedEvent();
        event.version = currentVersion + 1;
        event.amount = command.amountCredited;
        event.accountId = command.accountId;
        event.eventId = UUID.randomUUID().toString();
        return event;
    }

    private void verifyBalance(CreditAmountCommand command) {

    }

    private void computeThings(long delay) {
        sleep(delay);
    }
}
