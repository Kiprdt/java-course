package loganalyzer;

import loganalyzer.io.LogReader;
import loganalyzer.model.LogEntry;
import loganalyzer.model.LogFilter;
import loganalyzer.model.LogLevel;
import loganalyzer.service.LogAnalyzer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter INPUT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) throws Exception {
        System.out.print("Укажите путь к логу (.log или .zip): ");
        String path = scanner.nextLine();

        List<LogEntry> logs = LogReader.loadLogs(path);
        LogAnalyzer.generateReport(logs, "report.txt");

        LogFilter filter = new LogFilter();

        while (true) {
            System.out.println("\nМеню: 1-Даты, 2-Уровни, 3-Сортировка, 4-Показать результат, 5-Сбросить фильтр, 0-Выход");
            System.out.print("Выбор: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("От (yyyy-MM-dd HH:mm) [Enter пропустить]: ");
                    filter.from = parseDate(scanner.nextLine());
                    System.out.print("До (yyyy-MM-dd HH:mm) [Enter пропустить]: ");
                    filter.to = parseDate(scanner.nextLine());
                }
                case "2" -> {
                    System.out.print("Уровни через запятую (INFO, ERROR, WARN): ");
                    String[] lvls = scanner.nextLine().split(",");
                    filter.levels = new HashSet<>();
                    for (String l : lvls) {
                        try {
                            filter.levels.add(LogLevel.valueOf(l.trim().toUpperCase()));
                        } catch (IllegalArgumentException ignored) {}
                    }
                }
                case "3" -> {
                    System.out.print("Порядок сортировки (1 - ASC, 2 - DESC): ");
                    filter.order = scanner.nextLine().equals("2") ? LogFilter.Order.DESC : LogFilter.Order.ASC;
                }
                case "4" -> applyFiltersAndPrint(logs, filter);
                case "5" -> filter = new LogFilter();
                case "0" -> { return; }
                default -> System.out.println("Неизвестная команда.");
            }
        }
    }

    private static LocalDateTime parseDate(String input) {
        if (input.trim().isEmpty()) return null;
        try {
            return LocalDateTime.parse(input.trim(), INPUT_FMT);
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты. Пропущено.");
            return null;
        }
    }

    private static void applyFiltersAndPrint(List<LogEntry> logs, LogFilter filter) {
        Stream<LogEntry> stream = logs.stream();

        if (filter.from != null) {
            stream = stream.filter(log -> !log.getTimestamp().isBefore(filter.from));
        }
        if (filter.to != null) {
            stream = stream.filter(log -> !log.getTimestamp().isAfter(filter.to));
        }
        if (filter.levels != null && !filter.levels.isEmpty()) {
            stream = stream.filter(log -> filter.levels.contains(log.getLevel()));
        }

        Comparator<LogEntry> comp = Comparator.comparing(LogEntry::getTimestamp);
        if (filter.order == LogFilter.Order.DESC) {
            comp = comp.reversed();
        }

        List<LogEntry> result = stream.sorted(comp).toList();
        System.out.println("\nНайдено записей: " + result.size());
        result.forEach(System.out::println);
    }
}