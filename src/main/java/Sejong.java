import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Sejong chatbot.
 */
public class Sejong {
    private static final String LINE = "____________________________________________________________";

    /**
     * Runs the chatbot loop, storing user inputs and listing them on request.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {
            printGreeting();

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if ("bye".equals(input)) {
                    printFarewell();
                    break;
                }

                if ("list".equals(input)) {
                    printList(tasks);
                    continue;
                }

                if (input.startsWith("mark ")) {
                    Integer taskIndex = parseTaskIndex(input);
                    if (isInvalidTaskIndex(taskIndex, tasks.size())) {
                        printInvalidTaskNumber();
                        continue;
                    }
                    markTaskDone(tasks, taskIndex);
                    continue;
                }

                if (input.startsWith("unmark ")) {
                    Integer taskIndex = parseTaskIndex(input);
                    if (isInvalidTaskIndex(taskIndex, tasks.size())) {
                        printInvalidTaskNumber();
                        continue;
                    }
                    markTaskNotDone(tasks, taskIndex);
                    continue;
                }

                if (input.startsWith("todo ")) {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        printInvalidCommand();
                        continue;
                    }
                    addTodo(tasks, description);
                    continue;
                }

                if (input.startsWith("deadline ")) {
                    String remainder = input.substring(9).trim();
                    int byIndex = remainder.indexOf("/by");
                    if (byIndex == -1 || byIndex == 0) {
                        printInvalidCommand();
                        continue;
                    }
                    String description = remainder.substring(0, byIndex).trim();
                    String by = remainder.substring(byIndex + 3).trim();
                    if (description.isEmpty() || by.isEmpty()) {
                        printInvalidCommand();
                        continue;
                    }
                    addDeadline(tasks, description, by);
                    continue;
                }

                if (input.startsWith("event ")) {
                    String remainder = input.substring(6).trim();
                    int fromIndex = remainder.indexOf("/from");
                    int toIndex = remainder.indexOf("/to");
                    if (fromIndex == -1 || toIndex == -1 || fromIndex == 0 || toIndex <= fromIndex) {
                        printInvalidCommand();
                        continue;
                    }
                    String description = remainder.substring(0, fromIndex).trim();
                    String from = remainder.substring(fromIndex + 5, toIndex).trim();
                    String to = remainder.substring(toIndex + 3).trim();
                    if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        printInvalidCommand();
                        continue;
                    }
                    addEvent(tasks, description, from, to);
                    continue;
                }

                printInvalidCommand();
            }
        }
    }

    /**
     * Prints the greeting message.
     */
    private static void printGreeting() {
        printLine();
        System.out.println(" Hello! I'm Sejong");
        System.out.println(" What can I do for you?");
        printLine();
    }

    /**
     * Prints the farewell message.
     */
    private static void printFarewell() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prints the list of tasks.
     *
     * @param tasks Task list to display.
     */
    private static void printList(List<Task> tasks) {
        printLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
        printLine();
    }

    /**
     * Adds a new Todo task.
     *
     * @param tasks       Task list to update.
     * @param description Task description.
     */
    private static void addTodo(List<Task> tasks, String description) {
        Task task = new Todo(description);
        tasks.add(task);
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /**
     * Adds a new Deadline task.
     *
     * @param tasks       Task list to update.
     * @param description Task description.
     * @param by          Deadline date/time.
     */
    private static void addDeadline(List<Task> tasks, String description, String by) {
        Task task = new Deadline(description, by);
        tasks.add(task);
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /**
     * Adds a new Event task.
     *
     * @param tasks       Task list to update.
     * @param description Task description.
     * @param from        Start date/time.
     * @param to          End date/time.
     */
    private static void addEvent(List<Task> tasks, String description, String from, String to) {
        Task task = new Event(description, from, to);
        tasks.add(task);
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /**
     * Marks a task as done by index (0-based).
     *
     * @param tasks     Task list to update.
     * @param taskIndex Index of the task.
     */
    private static void markTaskDone(List<Task> tasks, int taskIndex) {
        Task task = tasks.get(taskIndex);
        task.markDone();
        printLine();
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("  " + task);
        printLine();
    }

    /**
     * Marks a task as not done by index (0-based).
     *
     * @param tasks     Task list to update.
     * @param taskIndex Index of the task.
     */
    private static void markTaskNotDone(List<Task> tasks, int taskIndex) {
        Task task = tasks.get(taskIndex);
        task.markNotDone();
        printLine();
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        printLine();
    }

    /**
     * Parses a task index from a command like "mark 2".
     *
     * @param input User input line.
     * @return Zero-based task index, or null if parsing fails.
     */
    private static Integer parseTaskIndex(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            return null;
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    /**
     * Checks if a parsed task index is invalid for the list size.
     *
     * @param taskIndex Parsed task index.
     * @param size      Task list size.
     * @return True if invalid; false otherwise.
     */
    private static boolean isInvalidTaskIndex(Integer taskIndex, int size) {
        return taskIndex == null || taskIndex < 0 || taskIndex >= size;
    }

    /**
     * Prints a consistent line separator.
     */
    private static void printLine() {
        System.out.println(LINE);
    }

    /**
     * Prints an invalid task number response.
     */
    private static void printInvalidTaskNumber() {
        printLine();
        System.out.println(" Invalid task number.");
        printLine();
    }

    /**
     * Prints an invalid command response.
     */
    private static void printInvalidCommand() {
        printLine();
        System.out.println(" Invalid command.");
        printLine();
    }
}
