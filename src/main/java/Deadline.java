/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private final String by;

    /**
     * Creates a Deadline task.
     *
     * @param description Task description.
     * @param by          Deadline date/time.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Creates a Deadline task with specified completion status.
     *
     * @param description Task description.
     * @param by          Deadline date/time.
     * @param isDone      Completion status.
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
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
        return getTaskIcon() + getStatusIcon() + " " + getDescription() + " (by: " + by + ")";
    }
}
