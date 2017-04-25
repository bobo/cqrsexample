package se.cleancode.Repository;

import org.springframework.stereotype.Component;
import se.cleancode.Event.Event;
import se.cleancode.Exception.ConcurentModificationException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class AccountRepository {
    Map<String, List<Event>> repository = new ConcurrentHashMap<>();

    public synchronized void save(Event event) {
        List<Event> events = repository.getOrDefault(event.accountId, new ArrayList<>());
        if (events.size() >= event.version) {
            throw new ConcurentModificationException();
        }
        events.add(event);
        repository.put(event.accountId, events);

    }

    public List<Event> get(String accountId) {
        List<Event> events = repository.get(accountId);
        if(events == null) {
            throw new RuntimeException("no such account");
        }
        return events;

    }

    public synchronized int getCurrentVersion(String accountId) {
        List<Event> events = repository.getOrDefault(accountId, new ArrayList<>());
        return events.size();
    }

    public List<Event> getEventsSince(OffsetDateTime since) {
        List<Event> allEvents = new ArrayList<>();
        repository.values().forEach(allEvents::addAll);
        return allEvents.stream()
                .filter(e -> e.occuredAt.isAfter(since))
                .sorted(Comparator.comparing(event -> event.occuredAt))
                .collect(Collectors.toList());

    }
}
