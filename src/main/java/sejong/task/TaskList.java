package sejong.task;

import sejong.SejongException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the task list and operations to manipulate it.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList with the given list of tasks.
     *
     * @param tasks List of tasks to initialize with.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list.
     *
     * @param index Index of the task to delete (0-based).
     * @return The deleted task.
     * @throws SejongException If index is invalid.
     */
    public Task deleteTask(int index) throws SejongException {
        if (index < 0 || index >= tasks.size()) {
            throw new SejongException("OOPS!!! Please provide a valid task number.");
        }
        return tasks.remove(index);
    }

    /**
     * Gets a task from the list.
     *
     * @param index Index of the task (0-based).
     * @return The task at the specified index.
     * @throws SejongException If index is invalid.
     */
    public Task getTask(int index) throws SejongException {
        if (index < 0 || index >= tasks.size()) {
            throw new SejongException("OOPS!!! Please provide a valid task number.");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task as done.
     *
     * @param index Index of the task to mark (0-based).
     * @return The marked task.
     * @throws SejongException If index is invalid.
     */
    public Task markTask(int index) throws SejongException {
        Task task = getTask(index);
        task.markDone();
        return task;
    }

    /**
     * Marks a task as not done.
     *
     * @param index Index of the task to unmark (0-based).
     * @return The unmarked task.
     * @throws SejongException If index is invalid.
     */
    public Task unmarkTask(int index) throws SejongException {
        Task task = getTask(index);
        task.markNotDone();
        return task;
    }

    /**
     * Finds all tasks on a specific date.
     *
     * @param date Date to search for.
     * @return List of tasks on that date.
     */
    public List<Task> findTasksOnDate(LocalDate date) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getBy().equals(date)) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (!date.isBefore(event.getFrom()) && !date.isAfter(event.getTo())) {
                    matchingTasks.add(task);
                }
            }
        }
        return matchingTasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the list of all tasks.
     *
     * @return List of tasks.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
