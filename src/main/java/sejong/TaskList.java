package sejong;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sejong.Messages.ERROR_INVALID_TASK_NUMBER;
import sejong.task.Deadline;
import sejong.task.Event;
import sejong.task.Task;

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
     * Creates a TaskList with the given tasks (varargs).
     *
     * @param tasks Tasks to initialize with.
     */
    public TaskList(Task... tasks) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
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
     * Checks if an equivalent task already exists in the list.
     * Duplicates are identified by: same type, matching description (case-insensitive),
     * and for Deadline/Event, matching date(s).
     *
     * @param task Task to check for duplicates.
     * @return True if a duplicate exists, false otherwise.
     */
    public boolean hasDuplicate(Task task) {
        for (Task existing : tasks) {
            if (isDuplicate(task, existing)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares two tasks for logical equivalence (duplicate check).
     */
    private boolean isDuplicate(Task newTask, Task existing) {
        if (newTask.getClass() != existing.getClass()) {
            return false;
        }
        if (!descriptionsMatch(newTask.getDescription(), existing.getDescription())) {
            return false;
        }
        if (newTask instanceof Deadline) {
            return ((Deadline) newTask).getBy().equals(((Deadline) existing).getBy());
        }
        if (newTask instanceof Event) {
            Event ne = (Event) newTask;
            Event oe = (Event) existing;
            return ne.getFrom().equals(oe.getFrom()) && ne.getTo().equals(oe.getTo());
        }
        return true; // Todo: same type and description
    }

    private static boolean descriptionsMatch(String a, String b) {
        return a != null && b != null
                && a.trim().equalsIgnoreCase(b.trim());
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
            throw new SejongException(ERROR_INVALID_TASK_NUMBER);
        }
        Task deletedTask = tasks.remove(index);
        assert deletedTask != null : "Deleted task should not be null";
        return deletedTask;
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
            throw new SejongException(ERROR_INVALID_TASK_NUMBER);
        }
        Task task = tasks.get(index);
        assert task != null : "Retrieved task should not be null";
        return task;
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
     * Finds all tasks containing the specified keyword in their description.
     *
     * @param keyword Keyword to search for (case-insensitive).
     * @return List of tasks containing the keyword.
     */
    public List<Task> findTasksByKeyword(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(task);
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
