package sejong;

import sejong.command.ByeCommand;
import sejong.command.Command;
import sejong.command.DeadlineCommand;
import sejong.command.DeleteCommand;
import sejong.command.EventCommand;
import sejong.command.FindCommand;
import sejong.command.ListCommand;
import sejong.command.MarkCommand;
import sejong.command.TodoCommand;
import sejong.command.UnmarkCommand;

import static sejong.Messages.*;


/**
 * Parses user input into commands and arguments.
 */
public class Parser {
    /** Command prefixes and their lengths. */
    private static final String CMD_TODO = "todo";
    private static final int CMD_TODO_LENGTH = 4;
    private static final String CMD_DEADLINE = "deadline";
    private static final int CMD_DEADLINE_LENGTH = 8;
    private static final String CMD_EVENT = "event";
    private static final int CMD_EVENT_LENGTH = 5;
    private static final String CMD_FIND = "find";
    private static final int CMD_FIND_LENGTH = 4;

    /**
     * Parses user input into a Command object.
     *
     * @param fullCommand Full user input.
     * @return The corresponding Command object.
     * @throws SejongException If the command is invalid.
     */
    public static Command parse(String fullCommand) throws SejongException {
        assert fullCommand != null : "Command should not be null";
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
            String keyword = parseFindCommand(fullCommand);
            return new FindCommand(keyword);
        }

        throw new SejongException(ERROR_UNKNOWN_COMMAND);
    }

    /**
     * Parses a task index from a command like "mark 2" or "delete 3".
     *
     * @param input User input line.
     * @return Zero-based task index.
     * @throws SejongException If the index cannot be parsed or is invalid format.
     */
    public static int parseTaskIndex(String input) throws SejongException {
        assert input != null : "Input should not be null";
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new SejongException(ERROR_INVALID_TASK_NUMBER);
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1; // Convert to 0-based
            assert index >= -1 : "Parsed index should be at least -1 (for user input 0)";
            return index;
        } catch (NumberFormatException e) {
            throw new SejongException(ERROR_INVALID_TASK_NUMBER);
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
        assert input != null : "Input should not be null";
        if (input.trim().equals(CMD_TODO)) {
            throw new SejongException(ERROR_EMPTY_TODO_DESCRIPTION);
        }
        String description = input.substring(CMD_TODO_LENGTH).trim();
        if (description.isEmpty()) {
            throw new SejongException(ERROR_EMPTY_TODO_DESCRIPTION);
        }
        assert !description.isEmpty() : "Description should not be empty at this point";
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
        assert input != null : "Input should not be null";
        if (input.trim().equals(CMD_DEADLINE)) {
            throw new SejongException(ERROR_EMPTY_DEADLINE_DESCRIPTION);
        }
        String remainder = input.substring(CMD_DEADLINE_LENGTH).trim();
        int byIndex = remainder.indexOf("/by");
        if (byIndex == -1) {
            throw new SejongException(ERROR_MISSING_DEADLINE_BY);
        }
        if (byIndex == 0) {
            throw new SejongException(ERROR_EMPTY_DEADLINE_DESCRIPTION);
        }
        String description = remainder.substring(0, byIndex).trim();
        String by = remainder.substring(byIndex + 3).trim();
        if (description.isEmpty()) {
            throw new SejongException(ERROR_EMPTY_DEADLINE_DESCRIPTION);
        }
        if (by.isEmpty()) {
            throw new SejongException(ERROR_EMPTY_DEADLINE_TIME);
        }
        String[] result = new String[]{description, by};
        assert result.length == 2 : "Result should have exactly 2 elements";
        assert !result[0].isEmpty() : "Description should not be empty";
        assert !result[1].isEmpty() : "By date should not be empty";
        return result;
    }

    /**
     * Parses an event command.
     *
     * @param input User input.
     * @return Array containing [description, from date, to date].
     * @throws SejongException If the command format is invalid.
     */
    public static String[] parseEventCommand(String input) throws SejongException {
        assert input != null : "Input should not be null";
        if (input.trim().equals(CMD_EVENT)) {
            throw new SejongException(ERROR_EMPTY_EVENT_DESCRIPTION);
        }
        String remainder = input.substring(CMD_EVENT_LENGTH).trim();
        int fromIndex = remainder.indexOf("/from");
        int toIndex = remainder.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new SejongException(ERROR_MISSING_EVENT_TIME);
        }
        if (fromIndex == 0) {
            throw new SejongException(ERROR_EMPTY_EVENT_DESCRIPTION);
        }
        if (toIndex <= fromIndex) {
            throw new SejongException(ERROR_WRONG_EVENT_ORDER);
        }
        String description = remainder.substring(0, fromIndex).trim();
        String from = remainder.substring(fromIndex + 5, toIndex).trim();
        String to = remainder.substring(toIndex + 3).trim();
        if (description.isEmpty()) {
            throw new SejongException(ERROR_EMPTY_EVENT_DESCRIPTION);
        }
        if (from.isEmpty()) {
            throw new SejongException(ERROR_EMPTY_EVENT_START);
        }
        if (to.isEmpty()) {
            throw new SejongException(ERROR_EMPTY_EVENT_END);
        }
        String[] result = new String[]{description, from, to};
        assert result.length == 3 : "Result should have exactly 3 elements";
        assert !result[0].isEmpty() : "Description should not be empty";
        assert !result[1].isEmpty() : "From date should not be empty";
        assert !result[2].isEmpty() : "To date should not be empty";
        return result;
    }

    /**
     * Parses a find command.
     *
     * @param input User input.
     * @return The keyword to search for.
     * @throws SejongException If the keyword is empty.
     */
    public static String parseFindCommand(String input) throws SejongException {
        assert input != null : "Input should not be null";
        if (input.trim().equals(CMD_FIND)) {
            throw new SejongException(ERROR_EMPTY_FIND_KEYWORD);
        }
        String keyword = input.substring(CMD_FIND_LENGTH).trim();
        if (keyword.isEmpty()) {
            throw new SejongException(ERROR_EMPTY_FIND_KEYWORD);
        }
        assert !keyword.isEmpty() : "Keyword should not be empty at this point";
        return keyword;
    }
}
