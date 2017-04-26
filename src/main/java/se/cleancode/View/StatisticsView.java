package se.cleancode.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.cleancode.Event.AccountCreatedEvent;
import se.cleancode.Event.AmountDebitedEvent;
import se.cleancode.Event.Event;
import se.cleancode.Repository.AccountRepository;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatisticsView {

    @Autowired
    AccountRepository repository;


    public Map<Integer,Long> calculateStatistics() {

        List<Event> events = repository.getEventsSince(OffsetDateTime.MIN);
        List<Event> collect = events.stream().filter(e -> e instanceof AmountDebitedEvent).collect(Collectors.toList());
        Map<Integer, Long> debitedPerSecond = new HashMap<>();
        collect.forEach(e -> {
            AmountDebitedEvent event = (AmountDebitedEvent) e;
            int second = event.occuredAt.getSecond();
            Long previousValue = debitedPerSecond.getOrDefault(second, 0L);
            debitedPerSecond.put(second, previousValue + event.amount);
        });
        return debitedPerSecond;
    }
}
