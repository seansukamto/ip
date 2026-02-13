package sejong;

import sejong.command.Command;

/**
 * Entry point for the Sejong chatbot.
 * Supports both CLI (run) and GUI (getResponse) usage.
 */
public class Sejong {
    private static final String FILE_PATH = "./data/sejong.txt";
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a new Sejong chatbot instance for CLI (console I/O).
     *
     * @param filePath Path to the storage file.
     */
    public Sejong(String filePath) {
        this(filePath, new Ui());
    }

    /**
     * Creates a new Sejong chatbot instance for GUI (output captured to the given buffer).
     *
     * @param filePath       Path to the storage file.
     * @param responseBuffer Buffer to capture bot responses for display in the GUI.
     */
    public Sejong(String filePath, StringBuilder responseBuffer) {
        this(filePath, new Ui(responseBuffer));
    }

    /**
     * Common constructor that handles initialization logic.
     *
     * @param filePath Path to the storage file.
     * @param ui       The user interface instance to use.
     */
    private Sejong(String filePath, Ui ui) {
        assert filePath != null : "File path should not be null";
        assert ui != null : "UI should not be null";
        
        this.ui = ui;
        this.storage = new Storage(filePath);
        this.tasks = loadTasksOrDefault();
        
        assert this.storage != null : "Storage should be initialized";
        assert this.tasks != null : "TaskList should be initialized";
    }

    /**
     * Loads tasks from storage, or returns an empty task list if loading fails.
     *
     * @return TaskList loaded from storage, or empty TaskList if loading fails.
     */
    private TaskList loadTasksOrDefault() {
        try {
            return new TaskList(storage.loadTasks());
        } catch (SejongException e) {
            ui.showLoadingError();
            return new TaskList();
        }
    }

    /**
     * Runs the chatbot.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit && ui.hasNextLine()) {
            try {
                String fullCommand = ui.readCommand();
                assert fullCommand != null : "Command should not be null";
                Command c = Parser.parse(fullCommand);
                assert c != null : "Parsed command should not be null";
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (SejongException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.close();
    }

    /**
     * Processes one user input and returns the bot's response (for GUI).
     * Empty input shows the welcome message.
     *
     * @param input User command string.
     * @return The bot's response text to display.
     */
    public String getResponse(String input) {
        ui.clearResponse();
        if (input == null || input.trim().isEmpty()) {
            ui.showWelcome();
            return ui.getAndClearResponse();
        }
        try {
            Command c = Parser.parse(input.trim());
            assert c != null : "Parsed command should not be null";
            c.execute(tasks, ui, storage);
            return ui.getAndClearResponse();
        } catch (SejongException e) {
            ui.showError(e.getMessage());
            return ui.getAndClearResponse();
        }
    }

    /**
     * Main entry point for CLI. For GUI, use Launcher instead.
     */
    public static void main(String... args) {
        new Sejong(FILE_PATH).run();
    }
}
