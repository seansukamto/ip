/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a task that is not done by default.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markNotDone() {
        isDone = false;
    }

    /**
     * Returns the status icon for the task.
     *
     * @return Status icon string.
     */
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
