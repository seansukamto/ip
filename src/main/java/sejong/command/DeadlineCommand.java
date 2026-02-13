package sejong.command;

import sejong.SejongException;
import sejong.Storage;
import sejong.Ui;
import sejong.task.Deadline;
import sejong.task.Task;
import sejong.TaskList;

import static sejong.Messages.ERROR_DUPLICATE_TASK;

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

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SejongException {
        Task task = new Deadline(description, by);
        if (tasks.hasDuplicate(task)) {
            throw new SejongException(ERROR_DUPLICATE_TASK);
        }
        tasks.addTask(task);
        storage.saveTasks(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
