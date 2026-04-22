package loganalyzer.service;

import loganalyzer.model.LogEntry;
import loganalyzer.model.LogLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class LogAnalyzer {
    public static void generateReport(List<LogEntry> logs, String outputPath) throws IOException {
        if (logs.isEmpty()) {
            Files.writeString(Path.of(outputPath), "Логи не найдены.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== Отчет по логам ===\n\n");

        sb.append("1. Общее количество логов: ").append(logs.size()).append("\n\n");

        sb.append("2. Количество по уровням:\n");
        logs.stream()
                .collect(Collectors.groupingBy(LogEntry::getLevel, Collectors.counting()))
                .forEach((k, v) -> sb.append("  ").append(k).append(": ").append(v).append("\n"));

        LocalDateTime minTime = logs.stream().map(LogEntry::getTimestamp).min(LocalDateTime::compareTo).orElse(null);
        LocalDateTime maxTime = logs.stream().map(LogEntry::getTimestamp).max(LocalDateTime::compareTo).orElse(null);
        sb.append("\n3. Промежуток логов:\n  Старт: ").append(minTime).append("\n  Конец: ").append(maxTime).append("\n\n");

        sb.append("4. Группировка по дням:\n");
        logs.stream()
                .collect(Collectors.groupingBy(l -> l.getTimestamp().toLocalDate(), TreeMap::new, Collectors.counting()))
                .forEach((k, v) -> sb.append("  ").append(k).append(": ").append(v).append("\n"));

        LocalDate worstDay = logs.stream()
                .filter(l -> l.getLevel() == LogLevel.ERROR)
                .collect(Collectors.groupingBy(l -> l.getTimestamp().toLocalDate(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
        sb.append("\n5. Самый ошибочный день: ").append(worstDay != null ? worstDay : "Нет ошибок").append("\n\n");

        sb.append("6. Топ сообщений:\n");
        logs.stream()
                .collect(Collectors.groupingBy(LogEntry::getMessage, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> sb.append("  ").append(e.getKey()).append(" — ").append(e.getValue()).append("\n"));

        Files.writeString(Path.of(outputPath), sb.toString());
        System.out.println("Аналитика сохранена в файл: " + outputPath);
    }
}