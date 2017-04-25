package se.cleancode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cleancode.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping(value = "failure", produces = "application/json")
public class FailuresController {


    @Autowired
    AccountCreatedCommandHandler accountCreatedCommandHandler;

    @Autowired
    AmountCreditedCommandHandler amountCreditedCommandHandler;

   @Autowired
    AmountDebitedCommandHandler amountDebitedCommandHandler;


    @Autowired
    AccountRepository repository;

    @Autowired
    AccountView view;


    @RequestMapping("{account-id}/credit")
    public AmountCreditedEvent creditAccount(@PathVariable("account-id") String accountId,
                                             @RequestBody long amount) {

        AmountCreditedCommand command = new AmountCreditedCommand(accountId,amount);
        return amountCreditedCommandHandler.handle(command, 5000L);

    }


}