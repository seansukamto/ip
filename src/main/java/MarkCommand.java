/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a MarkCommand for the specified task index.
     *
     * @param taskIndex Zero-based task index.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = tasks.markTask(taskIndex);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskMarked(task);
    }
}
