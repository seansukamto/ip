package sejong.command;

import sejong.SejongException;
import sejong.Storage;
import sejong.Ui;
import sejong.task.Task;
import sejong.task.TaskList;

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

    /**
     * Executes the mark command by marking the specified task as done.
     *
     * @param tasks   The task list.
     * @param ui      The user interface.
     * @param storage The storage handler.
     * @throws SejongException If the task index is invalid or there is an error saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = tasks.markTask(taskIndex);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskMarked(task);
    }
}
