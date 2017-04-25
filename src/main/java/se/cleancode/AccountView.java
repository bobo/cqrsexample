package se.cleancode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountView {

    @Autowired
    AccountRepository repository;


    public Account getAccount(String accountId) {

        List<Event> events =  repository.get(accountId);

        Account account = new Account();
        events.forEach(account::on);

        return account;
    }
}
