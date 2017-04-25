package se.cleancode.Messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.cleancode.Event.Event;
import se.cleancode.Repository.AccountRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;


@Component
public class MessageLogWithJob {

    private static final List<Event> messageLog = new ArrayList<>();

    private final static Logger LOG = LoggerFactory.getLogger(MessageLogWithJob.class);

    @Autowired
    AccountRepository repository;

    private OffsetDateTime lastSentMessage = OffsetDateTime.MIN;

    @Scheduled(fixedDelay = 3000)
    public void sendMessages() {
        List<Event> eventsSince = repository.getEventsSince(lastSentMessage);
        LOG.info("Sending {} messages",eventsSince.size());
        eventsSince.forEach(e -> {
                if(Math.random() < 0.5) {
                    appendMessageWithDelay(e);
                } else {
                    appendMessage(e);
                }
                if(lastSentMessage.isBefore(e.occuredAt)) {
                    lastSentMessage = e.occuredAt;
                }
        });
    }

    private void appendMessageWithDelay(Event e) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture<Boolean> job = executor.schedule(sendMessage(e), 10, SECONDS);
        awaitCompletion(job);
    }

    private void appendMessage(Event e) {
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
