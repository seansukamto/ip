package sejong.command;

import sejong.Storage;
import sejong.Ui;
import sejong.task.TaskList;

/**
 * Command to list all tasks.
 */
public class ListCommand extends Command {
    /**
     * Creates a new ListCommand.
     */
    public ListCommand() {
        // Default constructor
    }

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks   The task list.
     * @param ui      The user interface.
     * @param storage The storage handler (not used).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks.getTasks());
    }
}
