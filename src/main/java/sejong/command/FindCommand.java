package sejong.command;

import java.util.List;

import sejong.Storage;
import sejong.TaskList;
import sejong.Ui;
import sejong.task.Task;

/**
 * Command to find tasks by searching for a keyword in their descriptions.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a FindCommand for the specified keyword.
     *
     * @param keyword Keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> foundTasks = tasks.findTasksByKeyword(keyword);
        ui.showFoundTasksByKeyword(foundTasks);
    }
}
