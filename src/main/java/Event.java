/**
 * Represents a task that occurs during a specific time period.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an Event task.
     *
     * @param description Task description.
     * @param from        Start date/time.
     * @param to          End date/time.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an Event task with specified completion status.
     *
     * @param description Task description.
     * @param from        Start date/time.
     * @param to          End date/time.
     * @param isDone      Completion status.
     */
    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
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
        return getTaskIcon() + getStatusIcon() + " " + getDescription() + " (from: " + from + " to: " + to + ")";
    }
}
