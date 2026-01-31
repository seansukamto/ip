package sejong.command;

import sejong.SejongException;
import sejong.Storage;
import sejong.Ui;
import sejong.task.Task;
import sejong.task.TaskList;

/**
 * Command to delete a task.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a DeleteCommand for the specified task index.
     *
     * @param taskIndex Zero-based task index.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = tasks.deleteTask(taskIndex);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskDeleted(task, tasks.size());
    }
}
