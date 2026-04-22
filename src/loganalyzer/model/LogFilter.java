package loganalyzer.model;

import java.time.LocalDateTime;
import java.util.Set;

public class LogFilter {
    public enum Order { ASC, DESC }

    public LocalDateTime from;
    public LocalDateTime to;
    public Set<LogLevel> levels;
    public Order order = Order.ASC;
}