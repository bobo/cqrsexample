package se.cleancode.Messaging;

import org.springframework.stereotype.Component;
import se.cleancode.Event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;


@Component
public class MessageLog {
    private static final List<Event> messageLog = new ArrayList<>();
    public void appendMessageWithDelay(Event e) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture<Boolean> job = executor.schedule(sendMessage(e), 10, SECONDS);
        awaitCompletion(job);
    }

    public void appendMessage(Event e) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        Future<Boolean> job = executor.submit(sendMessage(e));
        awaitCompletion(job);
    }

    private Callable<Boolean> sendMessage(Event e) {
        return () -> messageLog.add(e);
    }

    public List<Event> getLog() {
        return messageLog;
    }


    private void awaitCompletion(Future<Boolean> schedule) {
        try {
            schedule.get();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


}
