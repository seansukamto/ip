package sejong.command;

import sejong.Storage;
import sejong.Ui;
import sejong.task.TaskList;

/**
 * Command to exit the program.
 */
public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
