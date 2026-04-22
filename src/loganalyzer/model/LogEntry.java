package loganalyzer.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private final LocalDateTime timestamp;
    private final LogLevel level;
    private final String message;

    public LogEntry(LocalDateTime timestamp, LogLevel level, String message) {
        this.timestamp = timestamp;
        this.level = level;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public LogLevel getLevel() { return level; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                " " + level + " " + message;
    }
}