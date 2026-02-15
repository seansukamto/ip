package sejong;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import sejong.task.Task;
import sejong.util.DateUtil;

/**
 * Handles interactions with the user.
 * Supports both CLI (System.out) and GUI (append to a StringBuilder) modes.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner;
    /** When non-null, show* methods append here instead of printing (GUI mode). */
    private final StringBuilder responseBuffer;

    /**
     * Creates a new Ui instance for CLI (console output).
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
        this.responseBuffer = null;
    }

    /**
     * Creates a new Ui instance for GUI that captures output to the given buffer.
     *
     * @param responseBuffer Buffer to append all shown messages to (e.g. for display in GUI).
     */
    public Ui(StringBuilder responseBuffer) {
        this.scanner = null;
        this.responseBuffer = responseBuffer;
    }

    /**
     * Reads a command from the user. Only valid for CLI mode.
     *
     * @return User input string.
     */
    public String readCommand() {
        return scanner != null && scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    /**
     * Checks if there is more input. Only valid for CLI mode.
     *
     * @return True if there is more input, false otherwise.
     */
    public boolean hasNextLine() {
        return scanner != null && scanner.hasNextLine();
    }

    /**
     * Closes the scanner. No-op in GUI mode.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Clears the captured response (GUI mode only). Use before running a command to capture only that command's output.
     */
    public void clearResponse() {
        if (responseBuffer != null) {
            responseBuffer.setLength(0);
        }
    }

    /**
     * Returns and clears the captured response (GUI mode only).
     * If not in GUI mode, returns empty string.
     *
     * @return The accumulated response text since last clear.
     */
    public String getAndClearResponse() {
        if (responseBuffer == null) {
            return "";
        }
        String s = responseBuffer.toString();
        responseBuffer.setLength(0);
        return s;
    }

    /**
     * Shows the greeting message.
     */
    public void showWelcome() {
        showLine();
        out(" Hello! I'm Sejong");
        out(" What can I do for you?");
        showLine();
    }

    /**
     * Shows the farewell message.
     */
    public void showGoodbye() {
        showLine();
        out(" Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Shows a line separator.
     */
    public void showLine() {
        out(LINE);
    }

    /**
     * Shows an error message.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        showLine();
        out(" " + message);
        showLine();
    }

    /**
     * Outputs a line. Uses responseBuffer in GUI mode, System.out in CLI mode.
     */
    private void out(String line) {
        if (responseBuffer != null) {
            responseBuffer.append(line).append("\n");
        } else {
            System.out.println(line);
        }
    }

    /**
     * Shows a loading error message.
     */
    public void showLoadingError() {
        showError("Error loading tasks from file.");
    }

    /**
     * Shows a task that was added.
     *
     * @param task Task that was added.
     * @param size New size of task list.
     */
    public void showTaskAdded(Task task, int size) {
        showLine();
        out(" Got it. I've added this task:");
        out("   " + task);
        out(" Now you have " + size + " " + getTaskWord(size) + " in the list.");
        showLine();
    }

    /**
     * Shows a task that was marked as done.
     *
     * @param task Task that was marked.
     */
    public void showTaskMarked(Task task) {
        showLine();
        out(" Nice! I've marked this task as done:");
        out("   " + task);
        showLine();
    }

    /**
     * Shows a task that was unmarked.
     *
     * @param task Task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        out(" OK, I've marked this task as not done yet:");
        out("   " + task);
        showLine();
    }

    /**
     * Shows a task that was deleted.
     *
     * @param task Task that was deleted.
     * @param size New size of task list.
     */
    public void showTaskDeleted(Task task, int size) {
        showLine();
        out(" Noted. I've removed this task:");
        out("   " + task);
        out(" Now you have " + size + " " + getTaskWord(size) + " in the list.");
        showLine();
    }

    /**
     * Shows all tasks in the list (varargs overload).
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(Task... tasks) {
        showTaskList(Arrays.asList(tasks));
    }

    /**
     * Shows all tasks in the list.
     *
     * @param tasks List of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            out(" Your task list is empty.");
        } else {
            out(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                out(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Shows tasks found on a specific date (varargs overload).
     *
     * @param date  Formatted date string.
     * @param tasks Matching tasks to display.
     */
    public void showFoundTasks(String date, Task... tasks) {
        showFoundTasks(Arrays.asList(tasks), date);
    }

    /**
     * Shows tasks found on a specific date.
     *
     * @param tasks List of matching tasks.
     * @param date  Formatted date string.
     */
    public void showFoundTasks(List<Task> tasks, String date) {
        showLine();
        out(" Here are the tasks on " + date + ":");
        if (tasks.isEmpty()) {
            out(" No tasks found on this date.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                out(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Shows tasks found by keyword search (varargs overload).
     *
     * @param tasks Matching tasks to display.
     */
    public void showFoundTasksByKeyword(Task... tasks) {
        showFoundTasksByKeyword(Arrays.asList(tasks));
    }

    /**
     * Shows tasks found by keyword search.
     *
     * @param tasks List of matching tasks.
     */
    public void showFoundTasksByKeyword(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            out(" No matching tasks found.");
        } else {
            out(" Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                out(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Shows tasks found using search criteria with filter context.
     *
     * Note: This method was added with AI-Assisted code development using Cursor.
     *
     * @param tasks    List of matching tasks.
     * @param criteria Search criteria that was applied.
     */
    public void showFoundTasks(List<Task> tasks, SearchCriteria criteria) {
        showLine();
        
        // Build filter description
        StringBuilder filterDesc = new StringBuilder(" Searching for tasks");
        boolean hasFilters = false;
        
        if (criteria.hasKeywords()) {
            filterDesc.append(" with keywords: ");
            filterDesc.append(String.join(", ", criteria.getKeywords()));
            hasFilters = true;
        }
        
        if (criteria.hasDateFilter()) {
            if (hasFilters) {
                filterDesc.append(",");
            }
            filterDesc.append(" on date: ");
            filterDesc.append(DateUtil.formatForDisplay(criteria.getDate()));
            hasFilters = true;
        }
        
        if (criteria.hasTypeFilter()) {
            if (hasFilters) {
                filterDesc.append(",");
            }
            filterDesc.append(" type: ");
            filterDesc.append(criteria.getTaskType().toString().toLowerCase());
            hasFilters = true;
        }
        
        if (criteria.hasStatusFilter()) {
            if (hasFilters) {
                filterDesc.append(",");
            }
            filterDesc.append(" status: ");
            filterDesc.append(criteria.getStatus().toString().toLowerCase());
        }
        
        out(filterDesc.toString());
        out("");
        
        if (tasks.isEmpty()) {
            out(" No matching tasks found.");
        } else {
            out(" Found " + tasks.size() + " matching " + getTaskWord(tasks.size()) + ":");
            for (int i = 0; i < tasks.size(); i++) {
                out(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Returns the correct singular or plural form of "task".
     *
     * @param count Number of tasks.
     * @return "task" if count is 1, "tasks" otherwise.
     */
    private String getTaskWord(int count) {
        return count == 1 ? "task" : "tasks";
    }
}
