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
import sejong.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static sejong.Constants.*;
import static sejong.Messages.*;

/**
 * Parses user input into commands and arguments.
 */
public class Parser {
    /** Command prefix lengths for parsing. */
    private static final int CMD_TODO_LENGTH = CMD_TODO.length();
    private static final int CMD_DEADLINE_LENGTH = CMD_DEADLINE.length();
    private static final int CMD_EVENT_LENGTH = CMD_EVENT.length();
    private static final int CMD_FIND_LENGTH = CMD_FIND.length();

    /**
     * Parses user input into a Command object.
     *
     * @param fullCommand Full user input.
     * @return The corresponding Command object.
     * @throws SejongException If the command is invalid.
     */
    public static Command parse(String fullCommand) throws SejongException {
        assert fullCommand != null : "Command should not be null";
        if (CMD_BYE.equals(fullCommand)) {
            return new ByeCommand();
        }

        if (CMD_LIST.equals(fullCommand)) {
            return new ListCommand();
        }

        if (fullCommand.startsWith(CMD_MARK + " ")) {
            int index = parseTaskIndex(fullCommand);
            return new MarkCommand(index);
        }

        if (fullCommand.startsWith(CMD_UNMARK + " ")) {
            int index = parseTaskIndex(fullCommand);
            return new UnmarkCommand(index);
        }

        if (fullCommand.startsWith(CMD_DELETE + " ")) {
            int index = parseTaskIndex(fullCommand);
            return new DeleteCommand(index);
        }

        if (fullCommand.equals(CMD_TODO) || fullCommand.startsWith(CMD_TODO + " ")) {
            String description = parseTodoCommand(fullCommand);
            return new TodoCommand(description);
        }

        if (fullCommand.equals(CMD_DEADLINE) || fullCommand.startsWith(CMD_DEADLINE + " ")) {
            String[] parts = parseDeadlineCommand(fullCommand);
            return new DeadlineCommand(parts[0], parts[1]);
        }

        if (fullCommand.equals(CMD_EVENT) || fullCommand.startsWith(CMD_EVENT + " ")) {
            String[] parts = parseEventCommand(fullCommand);
            return new EventCommand(parts[0], parts[1], parts[2]);
        }

        if (fullCommand.equals(CMD_FIND) || fullCommand.startsWith(CMD_FIND + " ")) {
            SearchCriteria criteria = parseFindCommand(fullCommand);
            return new FindCommand(criteria);
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
            int userIndex = Integer.parseInt(parts[1]);
            // Validate user input is positive and won't overflow when converted to 0-based
            if (userIndex <= 0 || userIndex == Integer.MAX_VALUE) {
                throw new SejongException(ERROR_INVALID_TASK_NUMBER);
            }
            int index = userIndex - 1; // Convert to 0-based
            assert index >= 0 : "Parsed index should be non-negative after validation";
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
     * Parses a find command with support for multiple keywords and filters.
     * Format: find <keywords> [/date DATE] [/type TYPE] [/status STATUS]
     *
     * Note: This method was enhanced with AI-Assisted code development using Cursor.
     *
     * @param input User input.
     * @return SearchCriteria object with parsed filters.
     * @throws SejongException If the command syntax is invalid.
     */
    public static SearchCriteria parseFindCommand(String input) throws SejongException {
        assert input != null : "Input should not be null";
        
        if (input.trim().equals(CMD_FIND)) {
            throw new SejongException(ERROR_EMPTY_FIND_KEYWORD);
        }
        
        String remainder = input.substring(CMD_FIND_LENGTH).trim();
        
        // Initialize filter variables
        List<String> keywords = new ArrayList<>();
        LocalDate date = null;
        SearchCriteria.TaskType taskType = SearchCriteria.TaskType.ALL;
        SearchCriteria.CompletionStatus status = SearchCriteria.CompletionStatus.ALL;
        
        // Split input into tokens
        String[] tokens = remainder.split("\\s+");
        
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            
            if (token.equals(FILTER_DATE)) {
                // Parse /date filter
                if (i + 1 >= tokens.length) {
                    throw new SejongException(ERROR_EMPTY_DATE_FILTER);
                }
                String dateStr = tokens[++i];
                date = DateUtil.parseDate(dateStr);
            } else if (token.equals(FILTER_TYPE)) {
                // Parse /type filter
                if (i + 1 >= tokens.length) {
                    throw new SejongException(ERROR_EMPTY_TYPE_FILTER);
                }
                String typeStr = tokens[++i].toLowerCase();
                taskType = parseTaskType(typeStr);
            } else if (token.equals(FILTER_STATUS)) {
                // Parse /status filter
                if (i + 1 >= tokens.length) {
                    throw new SejongException(ERROR_EMPTY_STATUS_FILTER);
                }
                String statusStr = tokens[++i].toLowerCase();
                status = parseCompletionStatus(statusStr);
            } else {
                // Regular keyword
                keywords.add(token);
            }
        }
        
        // Must have at least keywords or filters
        if (keywords.isEmpty() && date == null 
                && taskType == SearchCriteria.TaskType.ALL 
                && status == SearchCriteria.CompletionStatus.ALL) {
            throw new SejongException(ERROR_EMPTY_FIND_KEYWORD);
        }
        
        return new SearchCriteria(keywords, date, taskType, status);
    }

    /**
     * Parses a task type string into a TaskType enum.
     *
     * @param typeStr Task type string.
     * @return Corresponding TaskType enum value.
     * @throws SejongException If the task type is invalid.
     */
    private static SearchCriteria.TaskType parseTaskType(String typeStr) throws SejongException {
        switch (typeStr) {
        case "todo":
            return SearchCriteria.TaskType.TODO;
        case "deadline":
            return SearchCriteria.TaskType.DEADLINE;
        case "event":
            return SearchCriteria.TaskType.EVENT;
        default:
            throw new SejongException(ERROR_INVALID_TASK_TYPE);
        }
    }

    /**
     * Parses a completion status string into a CompletionStatus enum.
     *
     * @param statusStr Status string.
     * @return Corresponding CompletionStatus enum value.
     * @throws SejongException If the status is invalid.
     */
    private static SearchCriteria.CompletionStatus parseCompletionStatus(String statusStr) 
            throws SejongException {
        switch (statusStr) {
        case "done":
            return SearchCriteria.CompletionStatus.DONE;
        case "pending":
            return SearchCriteria.CompletionStatus.PENDING;
        default:
            throw new SejongException(ERROR_INVALID_STATUS);
        }
    }
}
