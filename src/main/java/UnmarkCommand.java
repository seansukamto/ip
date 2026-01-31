/**
 * Command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates an UnmarkCommand for the specified task index.
     *
     * @param taskIndex Zero-based task index.
     */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = tasks.unmarkTask(taskIndex);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskUnmarked(task);
    }
}
