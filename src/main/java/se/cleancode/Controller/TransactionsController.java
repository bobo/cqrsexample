package se.cleancode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cleancode.Event.Event;
import se.cleancode.Messaging.MessageLog;
import se.cleancode.Messaging.MessageLogWithJob;
import se.cleancode.View.TransactionView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping(value = "transactions", produces = "application/json")
public class TransactionsController {

    @Autowired
    TransactionView view;

    @RequestMapping(value = "{account-id}", method = GET)
    public long showLog(@PathVariable("account-id") String accountId) {
        return view.getAccount(accountId);
    }


}
