package sejong.command;

import sejong.Storage;
import sejong.Ui;
import sejong.task.Task;
import sejong.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Command to find tasks on a specific date.
 */
public class FindCommand extends Command {
    private final LocalDate date;

    /**
     * Creates a FindCommand for the specified date.
     *
     * @param date Date to search for.
     */
    public FindCommand(LocalDate date) {
        this.date = date;
    }

    /**
     * Executes the find command by searching for tasks on the specified date.
     *
     * @param tasks   The task list.
     * @param ui      The user interface.
     * @param storage The storage handler (not used).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        java.util.List<Task> foundTasks = tasks.findTasksOnDate(date);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        ui.showFoundTasks(foundTasks, formattedDate);
    }
}
