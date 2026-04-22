package loganalyzer.io;

import loganalyzer.model.LogEntry;
import loganalyzer.model.LogLevel;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LogReader {
    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\d{4}-\\d{2}-\\d{2}[T\\s]\\d{2}:\\d{2}:\\d{2}(?:[.,]\\d+)?)\\s+(INFO|WARN|ERROR|TRACE|DEBUG)\\s+(.*)$"
    );

    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.MICRO_OF_SECOND, 1, 6, true)
            .optionalEnd()
            .toFormatter();

    public static List<LogEntry> loadLogs(String filePath) throws IOException {
        Path path = Path.of(filePath);
        boolean isTemp = false;

        if (filePath.endsWith(".zip")) {
            path = extractFirstLogFromZip(path);
            isTemp = true;
        }

        List<LogEntry> entries;
        try (Stream<String> lines = Files.lines(path)) {
            entries = lines.map(LogReader::parseLine)
                    .filter(Objects::nonNull)
                    .toList();
        }

        if (isTemp) {
            Files.deleteIfExists(path);
        }
        return entries;
    }

    private static Path extractFirstLogFromZip(Path zipPath) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".log")) {
                    Path tempFile = Files.createTempFile("log_extract_", ".log");
                    Files.copy(zis, tempFile, StandardCopyOption.REPLACE_EXISTING);
                    return tempFile;
                }
            }
        }
        throw new FileNotFoundException("В архиве не найдено .log файлов");
    }

    private static LogEntry parseLine(String line) {
        Matcher matcher = LOG_PATTERN.matcher(line);
        if (!matcher.find()) return null;

        try {
            String timeStr = matcher.group(1).replace('T', ' ').replace(',', '.');
            LocalDateTime timestamp = LocalDateTime.parse(timeStr, DATE_FORMATTER);
            LogLevel level = LogLevel.valueOf(matcher.group(2));
            String message = matcher.group(3).trim();

            return new LogEntry(timestamp, level, message);
        } catch (Exception e) {
            return null;
        }
    }
}