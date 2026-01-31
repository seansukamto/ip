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

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        java.util.List<Task> foundTasks = tasks.findTasksOnDate(date);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        ui.showFoundTasks(foundTasks, formattedDate);
    }
}
