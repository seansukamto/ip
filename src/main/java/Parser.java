import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input into commands and arguments.
 */
public class Parser {

    /**
     * Parses user input into a Command object.
     *
     * @param fullCommand Full user input.
     * @return The corresponding Command object.
     * @throws SejongException If the command is invalid.
     */
    public static Command parse(String fullCommand) throws SejongException {
        if ("bye".equals(fullCommand)) {
            return new ByeCommand();
        }

        if ("list".equals(fullCommand)) {
            return new ListCommand();
        }

        if (fullCommand.startsWith("mark ")) {
            int index = parseTaskIndex(fullCommand);
            return new MarkCommand(index);
        }

        if (fullCommand.startsWith("unmark ")) {
            int index = parseTaskIndex(fullCommand);
            return new UnmarkCommand(index);
        }

        if (fullCommand.startsWith("delete ")) {
            int index = parseTaskIndex(fullCommand);
            return new DeleteCommand(index);
        }

        if (fullCommand.startsWith("todo")) {
            String description = parseTodoCommand(fullCommand);
            return new TodoCommand(description);
        }

        if (fullCommand.startsWith("deadline")) {
            String[] parts = parseDeadlineCommand(fullCommand);
            return new DeadlineCommand(parts[0], parts[1]);
        }

        if (fullCommand.startsWith("event")) {
            String[] parts = parseEventCommand(fullCommand);
            return new EventCommand(parts[0], parts[1], parts[2]);
        }

        if (fullCommand.startsWith("find")) {
            LocalDate date = parseFindCommand(fullCommand);
            return new FindCommand(date);
        }

        throw new SejongException("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    /**
     * Parses a task index from a command like "mark 2" or "delete 3".
     *
     * @param input User input line.
     * @return Zero-based task index.
     * @throws SejongException If the index cannot be parsed or is invalid format.
     */
    public static int parseTaskIndex(String input) throws SejongException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new SejongException("OOPS!!! Please provide a valid task number.");
        }
        try {
            return Integer.parseInt(parts[1]) - 1; // Convert to 0-based
        } catch (NumberFormatException e) {
            throw new SejongException("OOPS!!! Please provide a valid task number.");
        }
    }

    /**
     * Parses a todo command.
     *
     * @param input User input.
     * @return Task description.
     * @throws SejongException If the description is empty.
     */
    public static String parseTodoCommand(String input) throws SejongException {
        if (input.trim().equals("todo")) {
            throw new SejongException("OOPS!!! The description of a todo cannot be empty.");
        }
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new SejongException("OOPS!!! The description of a todo cannot be empty.");
        }
        return description;
    }

    /**
     * Parses a deadline command.
     *
     * @param input User input.
     * @return Array containing [description, by date].
     * @throws SejongException If the command format is invalid.
     */
    public static String[] parseDeadlineCommand(String input) throws SejongException {
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
        return new String[]{description, by};
    }

    /**
     * Parses an event command.
     *
     * @param input User input.
     * @return Array containing [description, from date, to date].
     * @throws SejongException If the command format is invalid.
     */
    public static String[] parseEventCommand(String input) throws SejongException {
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
        return new String[]{description, from, to};
    }

    /**
     * Parses a find command.
     *
     * @param input User input.
     * @return The date to search for.
     * @throws SejongException If the date format is invalid.
     */
    public static LocalDate parseFindCommand(String input) throws SejongException {
        if (input.trim().equals("find")) {
            throw new SejongException("OOPS!!! Please provide a date to search for (yyyy-MM-dd format).");
        }
        String dateStr = input.substring(4).trim();
        if (dateStr.isEmpty()) {
            throw new SejongException("OOPS!!! Please provide a date to search for (yyyy-MM-dd format).");
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new SejongException("Invalid date format! Please use yyyy-MM-dd format (e.g., 2019-12-02)");
        }
    }
}
