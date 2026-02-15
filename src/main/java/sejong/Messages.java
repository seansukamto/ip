package sejong;

/**
 * Contains all user-facing messages used by the Sejong chatbot.
 * Centralizes message strings for consistency and easier maintenance.
 */
public class Messages {
    // Error message prefix
    private static final String ERROR_PREFIX = "OOPS!!! ";

    // Command parsing errors
    public static final String ERROR_UNKNOWN_COMMAND = 
            ERROR_PREFIX + "I'm sorry, but I don't know what that means :-(";
    public static final String ERROR_INVALID_TASK_NUMBER = 
            ERROR_PREFIX + "Please provide a valid task number.";
    
    // Todo command errors
    public static final String ERROR_EMPTY_TODO_DESCRIPTION = 
            ERROR_PREFIX + "The description of a todo cannot be empty.";
    
    // Deadline command errors
    public static final String ERROR_EMPTY_DEADLINE_DESCRIPTION = 
            ERROR_PREFIX + "The description of a deadline cannot be empty.";
    public static final String ERROR_MISSING_DEADLINE_BY = 
            ERROR_PREFIX + "Please specify when the deadline is using /by.";
    public static final String ERROR_EMPTY_DEADLINE_TIME = 
            ERROR_PREFIX + "The deadline time cannot be empty.";
    
    // Event command errors
    public static final String ERROR_EMPTY_EVENT_DESCRIPTION = 
            ERROR_PREFIX + "The description of an event cannot be empty.";
    public static final String ERROR_MISSING_EVENT_TIME = 
            ERROR_PREFIX + "Please specify the event time using /from and /to.";
    public static final String ERROR_WRONG_EVENT_ORDER = 
            ERROR_PREFIX + "Please use /from before /to.";
    public static final String ERROR_EMPTY_EVENT_START = 
            ERROR_PREFIX + "The event start time cannot be empty.";
    public static final String ERROR_EMPTY_EVENT_END = 
            ERROR_PREFIX + "The event end time cannot be empty.";
    
    // Find command errors
    public static final String ERROR_EMPTY_FIND_KEYWORD = 
            ERROR_PREFIX + "Please provide a keyword to search for.";
    public static final String ERROR_INVALID_FIND_SYNTAX = 
            ERROR_PREFIX + "Invalid find command syntax. Use: find <keywords> [/date DATE] [/type TYPE] [/status STATUS]";
    public static final String ERROR_INVALID_TASK_TYPE = 
            ERROR_PREFIX + "Invalid task type. Use: todo, deadline, or event.";
    public static final String ERROR_INVALID_STATUS = 
            ERROR_PREFIX + "Invalid status. Use: done or pending.";
    public static final String ERROR_EMPTY_DATE_FILTER = 
            ERROR_PREFIX + "Please specify a date after /date.";
    public static final String ERROR_EMPTY_TYPE_FILTER = 
            ERROR_PREFIX + "Please specify a type after /type.";
    public static final String ERROR_EMPTY_STATUS_FILTER = 
            ERROR_PREFIX + "Please specify a status after /status.";

    // Duplicate task
    public static final String ERROR_DUPLICATE_TASK = 
            ERROR_PREFIX + "This task already exists in the list.";

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private Messages() {
        throw new AssertionError("Messages is a utility class and should not be instantiated");
    }
}
