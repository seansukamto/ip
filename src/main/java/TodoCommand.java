/**
 * Command to add a todo task.
 */
public class TodoCommand extends Command {
    private final String description;

    /**
     * Creates a TodoCommand with the specified description.
     *
     * @param description Task description.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = new Todo(description);
        tasks.addTask(task);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
