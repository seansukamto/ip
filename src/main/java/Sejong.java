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

                addTask(tasks, input);
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
     * Adds a new task using the given description.
     *
     * @param tasks       Task list to update.
     * @param description Task description.
     */
    private static void addTask(List<Task> tasks, String description) {
        Task task = new Task(description);
        tasks.add(task);
        printLine();
        System.out.println(" added: " + task);
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
}
