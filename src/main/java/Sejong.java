import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Sejong chatbot.
 */
public class Sejong {
    private static final String LINE = "____________________________________________________________";
    private final List<Task> tasks;

    /**
     * Creates a new Sejong chatbot instance.
     */
    public Sejong() {
        tasks = new ArrayList<>();
    }

    /**
     * Main entry point.
     */
    public static void main(String[] args) {
        Sejong sejong = new Sejong();
        try (Scanner scanner = new Scanner(System.in)) {
            sejong.printGreeting();

            while (scanner.hasNextLine()) {
                try {
                    String input = scanner.nextLine().trim();
                    if ("bye".equals(input)) {
                        sejong.printFarewell();
                        break;
                    }

                    if ("list".equals(input)) {
                        sejong.printList();
                        continue;
                    }

                    if (input.startsWith("mark ")) {
                        sejong.handleMarkCommand(input);
                        continue;
                    }

                    if (input.startsWith("unmark ")) {
                        sejong.handleUnmarkCommand(input);
                        continue;
                    }

                    if (input.startsWith("todo")) {
                        sejong.handleTodoCommand(input);
                        continue;
                    }

                    if (input.startsWith("deadline")) {
                        sejong.handleDeadlineCommand(input);
                        continue;
                    }

                    if (input.startsWith("event")) {
                        sejong.handleEventCommand(input);
                        continue;
                    }

                    throw new SejongException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                } catch (SejongException e) {
                    sejong.printError(e.getMessage());
                }
            }
        }
    }

    /**
     * Prints the greeting message.
     */
    private void printGreeting() {
        printLine();
        System.out.println(" Hello! I'm Sejong");
        System.out.println(" What can I do for you?");
        printLine();
    }

    /**
     * Prints the farewell message.
     */
    private void printFarewell() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prints the list of tasks.
     */
    private void printList() {
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
     * @param description Task description.
     */
    private void addTodo(String description) {
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
     * @param description Task description.
     * @param by          Deadline date/time.
     */
    private void addDeadline(String description, String by) {
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
     * @param description Task description.
     * @param from        Start date/time.
     * @param to          End date/time.
     */
    private void addEvent(String description, String from, String to) {
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
     * @param taskIndex Index of the task.
     */
    private void markTaskDone(int taskIndex) {
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
     * @param taskIndex Index of the task.
     */
    private void markTaskNotDone(int taskIndex) {
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
    private Integer parseTaskIndex(String input) {
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
     * Checks if a parsed task index is invalid.
     *
     * @param taskIndex Parsed task index.
     * @return True if invalid; false otherwise.
     */
    private boolean isInvalidTaskIndex(Integer taskIndex) {
        return taskIndex == null || taskIndex < 0 || taskIndex >= tasks.size();
    }

    /**
     * Handles the todo command.
     *
     * @param input User input.
     * @throws SejongException If the command format is invalid.
     */
    private void handleTodoCommand(String input) throws SejongException {
        if (input.trim().equals("todo")) {
            throw new SejongException("OOPS!!! The description of a todo cannot be empty.");
        }
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new SejongException("OOPS!!! The description of a todo cannot be empty.");
        }
        addTodo(description);
    }

    /**
     * Handles the deadline command.
     *
     * @param input User input.
     * @throws SejongException If the command format is invalid.
     */
    private void handleDeadlineCommand(String input) throws SejongException {
        if (input.trim().equals("deadline")) {
            throw new SejongException("OOPS!!! The description of a deadline cannot be empty.");
        }
        String remainder = input.substring(8).trim();
        int byIndex = remainder.indexOf("/by");
        if (byIndex == -1) {
            throw new SejongException("OOPS!!! Please specify when the deadline is using /by.");
        }
        if (byIndex == 0) {
            throw new SejongException("OOPS!!! The description of a deadline cannot be empty.");
        }
        String description = remainder.substring(0, byIndex).trim();
        String by = remainder.substring(byIndex + 3).trim();
        if (description.isEmpty()) {
            throw new SejongException("OOPS!!! The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new SejongException("OOPS!!! The deadline time cannot be empty.");
        }
        addDeadline(description, by);
    }

    /**
     * Handles the event command.
     *
     * @param input User input.
     * @throws SejongException If the command format is invalid.
     */
    private void handleEventCommand(String input) throws SejongException {
        if (input.trim().equals("event")) {
            throw new SejongException("OOPS!!! The description of an event cannot be empty.");
        }
        String remainder = input.substring(5).trim();
        int fromIndex = remainder.indexOf("/from");
        int toIndex = remainder.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new SejongException("OOPS!!! Please specify the event time using /from and /to.");
        }
        if (fromIndex == 0) {
            throw new SejongException("OOPS!!! The description of an event cannot be empty.");
        }
        if (toIndex <= fromIndex) {
            throw new SejongException("OOPS!!! Please use /from before /to.");
        }
        String description = remainder.substring(0, fromIndex).trim();
        String from = remainder.substring(fromIndex + 5, toIndex).trim();
        String to = remainder.substring(toIndex + 3).trim();
        if (description.isEmpty()) {
            throw new SejongException("OOPS!!! The description of an event cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new SejongException("OOPS!!! The event start time cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new SejongException("OOPS!!! The event end time cannot be empty.");
        }
        addEvent(description, from, to);
    }

    /**
     * Handles the mark command.
     *
     * @param input User input.
     * @throws SejongException If the task number is invalid.
     */
    private void handleMarkCommand(String input) throws SejongException {
        Integer taskIndex = parseTaskIndex(input);
        if (isInvalidTaskIndex(taskIndex)) {
            throw new SejongException("OOPS!!! Please provide a valid task number.");
        }
        markTaskDone(taskIndex);
    }

    /**
     * Handles the unmark command.
     *
     * @param input User input.
     * @throws SejongException If the task number is invalid.
     */
    private void handleUnmarkCommand(String input) throws SejongException {
        Integer taskIndex = parseTaskIndex(input);
        if (isInvalidTaskIndex(taskIndex)) {
            throw new SejongException("OOPS!!! Please provide a valid task number.");
        }
        markTaskNotDone(taskIndex);
    }

    /**
     * Prints a consistent line separator.
     */
    private void printLine() {
        System.out.println(LINE);
    }

    /**
     * Prints an error message.
     *
     * @param message Error message to display.
     */
    private void printError(String message) {
        printLine();
        System.out.println(" " + message);
        printLine();
    }
}
