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

    @Override
    public String getTaskIcon() {
        return "[E]";
    }

    @Override
    public String toString() {
        return getTaskIcon() + getStatusIcon() + " " + getDescription() + " (from: " + from + " to: " + to + ")";
    }
}
