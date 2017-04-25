package se.cleancode.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cleancode.*;
import se.cleancode.Messaging.MessageLog;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping(value = "messagelog", produces = "application/json")
public class MessageController {


    @Autowired
    MessageLog messageLog;


    @RequestMapping(value = "", method = GET)
    public List<Event> showLog() {
        return messageLog.getLog();
    }


}
