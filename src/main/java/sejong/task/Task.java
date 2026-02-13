package sejong.task;

/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a task that is not done by default.
     *
     * @param description Task description.
     */
    public Task(String description) {
        assert description != null : "Description should not be null";
        assert !description.isEmpty() : "Description should not be empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a task with specified completion status.
     *
     * @param description Task description.
     * @param isDone      Completion status.
     */
    public Task(String description, boolean isDone) {
        assert description != null : "Description should not be null";
        assert !description.isEmpty() : "Description should not be empty";
        this.description = description;
        this.isDone = isDone;
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
     * Returns the task description.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status icon for the task.
     *
     * @return Status icon string.
     */
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns the task type icon.
     *
     * @return Task type icon string.
     */
    public abstract String getTaskIcon();

    /**
     * Converts the task to file storage format.
     *
     * @return String representation for file storage.
     */
    public abstract String toFileFormat();

    /**
     * Checks if the task is done.
     *
     * @return True if done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return getTaskIcon() + getStatusIcon() + " " + getDescription();
    }
}
