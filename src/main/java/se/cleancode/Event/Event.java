package se.cleancode.Event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Event {

    public long version;

    public String accountId;

    public String eventId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public OffsetDateTime occuredAt = OffsetDateTime.now();
}
