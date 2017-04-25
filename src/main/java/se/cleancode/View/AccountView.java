package se.cleancode.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.cleancode.Aggregate.Account;
import se.cleancode.Event.Event;
import se.cleancode.Repository.AccountRepository;

import java.util.List;

@Component
public class AccountView {

    @Autowired
    AccountRepository repository;


    public Account getAccount(String accountId) {

        List<Event> events =  repository.get(accountId);
        Account account = Account.fromEvents(events);

        return account;
    }
}
