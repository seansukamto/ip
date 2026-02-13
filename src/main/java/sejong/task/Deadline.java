package sejong.task;

import java.time.LocalDate;

import sejong.SejongException;
import sejong.util.DateUtil;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Creates a Deadline task.
     *
     * @param description Task description.
     * @param by          Deadline date in yyyy-MM-dd format.
     * @throws SejongException If date format is invalid.
     */
    public Deadline(String description, String by) throws SejongException {
        super(description);
        this.by = DateUtil.parseDate(by);
    }

    /**
     * Creates a Deadline task with specified completion status.
     *
     * @param description Task description.
     * @param by          Deadline date in yyyy-MM-dd format.
     * @param isDone      Completion status.
     * @throws SejongException If date format is invalid.
     */
    public Deadline(String description, String by, boolean isDone) throws SejongException {
        super(description, isDone);
        this.by = DateUtil.parseDate(by);
    }

    /**
     * Creates a Deadline task with LocalDate object.
     *
     * @param description Task description.
     * @param by          Deadline date.
     * @param isDone      Completion status.
     */
    public Deadline(String description, LocalDate by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Returns the deadline date.
     *
     * @return Deadline date.
     */
    public LocalDate getBy() {
        return by;
    }

    @Override
    public String getTaskIcon() {
        return "[D]";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + by;
    }

    @Override
    public String toString() {
        String formattedDate = DateUtil.formatForDisplay(by);
        return getTaskIcon() + getStatusIcon() + " " + getDescription() + " (by: " + formattedDate + ")";
    }
}
