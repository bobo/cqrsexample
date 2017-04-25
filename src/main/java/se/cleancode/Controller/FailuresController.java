package se.cleancode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cleancode.Command.*;
import se.cleancode.Event.AccountCreatedEvent;
import se.cleancode.Event.AmountCreditedEvent;
import se.cleancode.Event.AmountDebitedEvent;
import se.cleancode.Handler.CreateAccountCommandHandler;
import se.cleancode.Handler.CreditAccountCommandHandler;
import se.cleancode.Handler.DebitAccountCommandHandler;
import se.cleancode.Repository.AccountRepository;
import se.cleancode.View.AccountView;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping(value = "failure", produces = "application/json")
public class FailuresController {


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


    @RequestMapping(value = "{account-id}/credit", method = POST)
    public AmountCreditedEvent creditAccount(@PathVariable("account-id") String accountId,
                                             @RequestBody long amount) {

        CreditAmountCommand command = new CreditAmountCommand(accountId,amount);
        return creditAccountCommandHandler.handle(command, 5000L);

    }

    @RequestMapping(value = "{account-id}/debit", method = POST)
    public AmountDebitedEvent debitAccount(@PathVariable("account-id") String accountId,
                                           @RequestBody long amount) {

        DebitAmountCommand command = new DebitAmountCommand(accountId,amount);
        return debitAccountCommandHandler.handle(command,5000L);

    }

    @RequestMapping("create")
    public AccountCreatedEvent createAccount() {

        CreateAccountCommand command = new CreateAccountCommand(UUID.randomUUID().toString());
        AccountCreatedEvent event = createAccountCommandHandler.handle(command,1);
        return event;

    }



}
