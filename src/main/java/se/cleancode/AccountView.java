package se.cleancode;

import java.util.List;

public class AccountView {

    AccountRepository repository;


    public Account getAccount(String accountId) {

        List<Event> events =  repository.get(accountId);

        Account account = new Account();
        events.forEach(account::on);

        return account;
    }
}
