package se.cleancode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cleancode.Aggregate.Account;
import se.cleancode.Command.*;
import se.cleancode.Event.AccountCreatedEvent;
import se.cleancode.Event.AmountCreditedEvent;
import se.cleancode.Event.AmountDebitedEvent;
import se.cleancode.Event.Event;
import se.cleancode.Handler.CreateAccountCommandHandler;
import se.cleancode.Handler.CreditAccountCommandHandler;
import se.cleancode.Handler.DebitAccountCommandHandler;
import se.cleancode.Repository.AccountRepository;
import se.cleancode.View.AccountView;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping(value = "account", produces = "application/json")
public class AccountController {


    @Autowired
    CreateAccountCommandHandler createAccountCommandHandler;

    @Autowired
    CreditAccountCommandHandler creditAccountCommandHandler;

   @Autowired
   DebitAccountCommandHandler debitAccountCommandHandler;


    @Autowired
    AccountRepository repository;

    @Autowired
    AccountView view;

    @RequestMapping("create")
    public AccountCreatedEvent createAccount() {

        CreateAccountCommand command = new CreateAccountCommand(UUID.randomUUID().toString());
        AccountCreatedEvent event = createAccountCommandHandler.handle(command);
        return event;

    }

    @RequestMapping("{account-id}/create")
    public AccountCreatedEvent createAccount(@PathVariable("account-id") String accountId) {

        CreateAccountCommand command = new CreateAccountCommand(accountId);
        AccountCreatedEvent event = createAccountCommandHandler.handle(command);
        return event;

    }


    @RequestMapping("{account-id}/credit")
    public AmountCreditedEvent creditAccount(@PathVariable("account-id") String accountId,
                                             @RequestBody long amount) {

        CreditAmountCommand command = new CreditAmountCommand(accountId,amount);
        return creditAccountCommandHandler.handle(command);

    }



    @RequestMapping("{account-id}/debit")
    public AmountDebitedEvent debitAccount(@PathVariable("account-id") String accountId,
                                           @RequestBody long amount) {

        DebitAmountCommand command = new DebitAmountCommand(accountId,amount);
        return debitAccountCommandHandler.handle(command);

    }

    @RequestMapping("{account-id}/list")
    public List<Event> events(@PathVariable("account-id") String accountId) {
        return repository.get(accountId);
    }


    @RequestMapping(value = "{account-id}", method = GET)
    public Account view(@PathVariable("account-id") String accountId) {
        return view.getAccount(accountId);
    }



}
