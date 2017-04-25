package se.cleancode.Aggregate;

import se.cleancode.Event.AccountCreatedEvent;
import se.cleancode.Event.AmountCreditedEvent;
import se.cleancode.Event.AmountDebitedEvent;
import se.cleancode.Event.Event;

import java.util.List;

public class Account {
    public String id;
    public long balance;


    public static Account fromEvents(List<Event> events){
        Account account = new Account();
        events.forEach(account::on);
        return account;
    }


    private void on(Event e) {
        if(e instanceof AccountCreatedEvent) {
            this.id = e.accountId;
            this.balance = 0;
        }
        if(e instanceof AmountCreditedEvent) {
            this.balance += ((AmountCreditedEvent) e).amount;
        }
        if(e instanceof AmountDebitedEvent) {
            this.balance -= ((AmountDebitedEvent) e).amount;
        }

    }
}
