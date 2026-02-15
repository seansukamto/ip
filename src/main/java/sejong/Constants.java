package sejong;

/**
 * Contains application-wide constants used by the Sejong chatbot.
 * Centralizes configuration values for consistency and easier maintenance.
 */
public class Constants {
    /** Default file path for task storage. */
    public static final String DEFAULT_FILE_PATH = "./data/sejong.txt";
    
    /** Command strings used in the application. */
    public static final String CMD_BYE = "bye";
    public static final String CMD_LIST = "list";
    public static final String CMD_MARK = "mark";
    public static final String CMD_UNMARK = "unmark";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_TODO = "todo";
    public static final String CMD_DEADLINE = "deadline";
    public static final String CMD_EVENT = "event";
    public static final String CMD_FIND = "find";
    
    /** Find command filter prefixes. */
    public static final String FILTER_DATE = "/date";
    public static final String FILTER_TYPE = "/type";
    public static final String FILTER_STATUS = "/status";

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private Constants() {
        throw new AssertionError("Constants is a utility class and should not be instantiated");
    }
}
