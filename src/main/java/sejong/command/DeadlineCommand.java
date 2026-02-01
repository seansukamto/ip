package sejong.command;

import sejong.SejongException;
import sejong.Storage;
import sejong.Ui;
import sejong.task.Deadline;
import sejong.task.Task;
import sejong.task.TaskList;

/**
 * Command to add a deadline task.
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final String by;

    /**
     * Creates a DeadlineCommand with the specified description and deadline.
     *
     * @param description Task description.
     * @param by          Deadline date.
     */
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Executes the deadline command by creating and adding a new deadline task.
     *
     * @param tasks   The task list.
     * @param ui      The user interface.
     * @param storage The storage handler.
     * @throws SejongException If there is an error with the date format or saving the task.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = new Deadline(description, by);
        tasks.addTask(task);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
