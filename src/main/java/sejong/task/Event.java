package sejong.task;

import java.time.LocalDate;

import sejong.SejongException;
import sejong.util.DateUtil;

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
     * @throws SejongException If date format is invalid or date range is invalid.
     */
    public Event(String description, String from, String to) throws SejongException {
        super(description);
        this.from = DateUtil.parseDate(from);
        this.to = DateUtil.parseDate(to);
        DateUtil.validateDateRange(this.from, this.to);
    }

    /**
     * Creates an Event task with specified completion status.
     *
     * @param description Task description.
     * @param from        Start date in yyyy-MM-dd format.
     * @param to          End date in yyyy-MM-dd format.
     * @param isDone      Completion status.
     * @throws SejongException If date format is invalid or date range is invalid.
     */
    public Event(String description, String from, String to, boolean isDone) throws SejongException {
        super(description, isDone);
        this.from = DateUtil.parseDate(from);
        this.to = DateUtil.parseDate(to);
        DateUtil.validateDateRange(this.from, this.to);
    }

    /**
     * Creates an Event task with LocalDate objects.
     *
     * @param description Task description.
     * @param from        Start date.
     * @param to          End date.
     * @param isDone      Completion status.
     * @throws SejongException If date range is invalid.
     */
    public Event(String description, LocalDate from, LocalDate to, boolean isDone) throws SejongException {
        super(description, isDone);
        this.from = from;
        this.to = to;
        DateUtil.validateDateRange(from, to);
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
        String formattedFrom = DateUtil.formatForDisplay(from);
        String formattedTo = DateUtil.formatForDisplay(to);
        return getTaskIcon() + getStatusIcon() + " " + getDescription()
                + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }
}
