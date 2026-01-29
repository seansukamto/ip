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

    @Override
    public String getTaskIcon() {
        return "[T]";
    }
}
