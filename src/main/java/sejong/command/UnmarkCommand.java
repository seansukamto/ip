package sejong.command;

import sejong.SejongException;
import sejong.Storage;
import sejong.Ui;
import sejong.task.Task;
import sejong.task.TaskList;

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

    /**
     * Executes the unmark command by marking the specified task as not done.
     *
     * @param tasks   The task list.
     * @param ui      The user interface.
     * @param storage The storage handler.
     * @throws SejongException If the task index is invalid or there is an error saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = tasks.unmarkTask(taskIndex);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskUnmarked(task);
    }
}
