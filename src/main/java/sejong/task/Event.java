package sejong.task;

import sejong.SejongException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that occurs during a specific time period.
 */
public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    /**
     * Creates an Event task.
     *
     * @param description Task description.
     * @param from        Start date in yyyy-MM-dd format.
     * @param to          End date in yyyy-MM-dd format.
     * @throws SejongException If date format is invalid.
     */
    public Event(String description, String from, String to) throws SejongException {
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    /**
     * Creates an Event task with specified completion status.
     *
     * @param description Task description.
     * @param from        Start date in yyyy-MM-dd format.
     * @param to          End date in yyyy-MM-dd format.
     * @param isDone      Completion status.
     * @throws SejongException If date format is invalid.
     */
    public Event(String description, String from, String to, boolean isDone) throws SejongException {
        super(description, isDone);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    /**
     * Creates an Event task with LocalDate objects.
     *
     * @param description Task description.
     * @param from        Start date.
     * @param to          End date.
     * @param isDone      Completion status.
     */
    public Event(String description, LocalDate from, LocalDate to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Parses a date string in yyyy-MM-dd format.
     *
     * @param dateStr Date string.
     * @return Parsed LocalDate.
     * @throws SejongException If format is invalid.
     */
    private LocalDate parseDate(String dateStr) throws SejongException {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new SejongException("Invalid date format! Please use yyyy-MM-dd format (e.g., 2019-12-02)");
        }
    }

    /**
     * Returns the start date.
     *
     * @return Start date.
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the end date.
     *
     * @return End date.
     */
    public LocalDate getTo() {
        return to;
    }

    @Override
    public String getTaskIcon() {
        return "[E]";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        String formattedFrom = from.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        String formattedTo = to.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return getTaskIcon() + getStatusIcon() + " " + getDescription() 
               + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }
}
