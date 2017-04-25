package se.cleancode.Messaging;

import org.springframework.stereotype.Component;
import se.cleancode.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;


@Component
public class MessageLog {

    private static final List<Event> messageLog = new ArrayList<>();

    public void appendMessageWithDelay(Event e) {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
           executor.schedule(sendMessage(e),10, SECONDS);
    }

    public void appendMessage(Event e) {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
           executor.submit(sendMessage(e));
    }

    private Callable<Boolean> sendMessage(Event e) {
        return () -> messageLog.add(e);
    }

    public List<Event> getLog() {
        return messageLog;
    }


}
