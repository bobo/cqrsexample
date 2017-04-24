package se.cleancode;

public class Account {
    public String id;
    public long balance;


    public void on(Event e) {


        if(e instanceof AccountCreatedEvent) {
            this.id = e.accountId;
            this.balance = 0;
        }
        if(e instanceof  AmountCreditedEvent) {
            this.balance += ((AmountCreditedEvent) e).amount;
        }
        if(e instanceof  AmountDebitedEvent) {
            this.balance -= ((AmountDebitedEvent) e).amount;
        }

    }
}
