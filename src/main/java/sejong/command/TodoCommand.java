package sejong.command;

import sejong.SejongException;
import sejong.Storage;
import sejong.Ui;
import sejong.task.Task;
import sejong.task.TaskList;
import sejong.task.Todo;

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

    /**
     * Executes the todo command by creating and adding a new todo task.
     *
     * @param tasks   The task list.
     * @param ui      The user interface.
     * @param storage The storage handler.
     * @throws SejongException If there is an error saving the task.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = new Todo(description);
        tasks.addTask(task);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
