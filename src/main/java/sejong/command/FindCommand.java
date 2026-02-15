package sejong.command;

import java.util.List;

import sejong.SearchCriteria;
import sejong.Storage;
import sejong.TaskList;
import sejong.Ui;
import sejong.task.Task;

/**
 * Command to find tasks using flexible search criteria.
 * Supports searching by keywords, date, task type, and completion status.
 * 
 * Note: This file was enhanced with AI-Assisted code development using Cursor.
 */
public class FindCommand extends Command {
    private final SearchCriteria criteria;

    /**
     * Creates a FindCommand with the specified search criteria.
     *
     * @param criteria Search criteria to apply.
     */
    public FindCommand(SearchCriteria criteria) {
        assert criteria != null : "Search criteria should not be null";
        this.criteria = criteria;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> foundTasks = tasks.findTasks(criteria);
        ui.showFoundTasks(foundTasks, criteria);
    }
}
