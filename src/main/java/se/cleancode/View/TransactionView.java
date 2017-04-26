package se.cleancode.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.cleancode.Aggregate.Account;
import se.cleancode.Event.AccountCreatedEvent;
import se.cleancode.Event.Event;
import se.cleancode.Repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionView {

    @Autowired
    AccountRepository repository;


    public long getAccount(String accountId) {

        List<Event> events =  repository.get(accountId);
        return events.stream().filter(e -> !(e instanceof AccountCreatedEvent)).count();
    }
}
