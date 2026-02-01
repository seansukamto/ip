package sejong.task;

/**
 * Represents a todo task without any date/time attached.
 */
public class Todo extends Task {
    /**
     * Creates a Todo task.
     *
     * @param description Task description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a Todo task with specified completion status.
     *
     * @param description Task description.
     * @param isDone      Completion status.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the task type icon for a Todo task.
     *
     * @return Task type icon "[T]".
     */
    @Override
    public String getTaskIcon() {
        return "[T]";
    }

    /**
     * Converts the task to file storage format.
     *
     * @return String representation in format: "T | STATUS | DESCRIPTION".
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }
}
