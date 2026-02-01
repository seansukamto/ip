package sejong.command;

import sejong.Storage;
import sejong.Ui;
import sejong.task.TaskList;

/**
 * Command to exit the program.
 */
public class ByeCommand extends Command {
    /**
     * Creates a new ByeCommand.
     */
    public ByeCommand() {
        // Default constructor
    }

    /**
     * Executes the bye command by displaying goodbye message.
     *
     * @param tasks   The task list (not used).
     * @param ui      The user interface.
     * @param storage The storage handler (not used).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Returns true to indicate the program should exit.
     *
     * @return True, indicating this command exits the program.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
