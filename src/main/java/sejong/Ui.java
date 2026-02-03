package sejong;

import java.util.List;
import java.util.Scanner;

import sejong.task.Task;

/**
 * Handles interactions with the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner;

    /**
     * Creates a new Ui instance.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user.
     *
     * @return User input string.
     */
    public String readCommand() {
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "";
    }

    /**
     * Checks if there is more input.
     *
     * @return True if there is more input, false otherwise.
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Shows the greeting message.
     */
    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Sejong");
        System.out.println(" What can I do for you?");
        showLine();
    }

    /**
     * Shows the farewell message.
     */
    public void showGoodbye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Shows a line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Shows an error message.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        showLine();
        System.out.println(" " + message);
        showLine();
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
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " " + getTaskWord(size) + " in the list.");
        showLine();
    }

    /**
     * Shows a task that was marked as done.
     *
     * @param task Task that was marked.
     */
    public void showTaskMarked(Task task) {
        showLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        showLine();
    }

    /**
     * Shows a task that was unmarked.
     *
     * @param task Task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        showLine();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
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
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " " + getTaskWord(size) + " in the list.");
        showLine();
    }

    /**
     * Shows all tasks in the list.
     *
     * @param tasks List of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        showLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /**
     * Shows tasks found on a specific date.
     *
     * @param tasks List of matching tasks.
     * @param date  Formatted date string.
     */
    public void showFoundTasks(List<Task> tasks, String date) {
        showLine();
        System.out.println(" Here are the tasks on " + date + ":");
        if (tasks.isEmpty()) {
            System.out.println(" No tasks found on this date.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Shows tasks found by keyword search.
     *
     * @param tasks List of matching tasks.
     */
    public void showFoundTasksByKeyword(List<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println(" No matching tasks found.");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
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
