package se.cleancode;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountRepository {
    Map<String, List<Event>> repository = new ConcurrentHashMap<>();

    public synchronized void save(Event event) {
        List<Event> events = repository.getOrDefault(event.accountId, new ArrayList<>());
        if (events.size() > event.version) {
            throw new VersionConflicException();
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
}
