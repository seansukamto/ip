package sejong.command;

import sejong.Storage;
import sejong.Ui;
import sejong.task.TaskList;

/**
 * Command to list all tasks.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks.getTasks());
    }
}
