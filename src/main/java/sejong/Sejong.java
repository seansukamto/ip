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
        ui = new Ui();
        storage = new Storage(filePath);
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.loadTasks());
        } catch (SejongException e) {
            ui.showLoadingError();
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
    }

    /**
     * Creates a new Sejong chatbot instance for GUI (output captured to the given buffer).
     *
     * @param filePath       Path to the storage file.
     * @param responseBuffer Buffer to capture bot responses for display in the GUI.
     */
    public Sejong(String filePath, StringBuilder responseBuffer) {
        ui = new Ui(responseBuffer);
        storage = new Storage(filePath);
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.loadTasks());
        } catch (SejongException e) {
            ui.showLoadingError();
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
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
                Command c = Parser.parse(fullCommand);
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
    public static void main(String[] args) {
        new Sejong(FILE_PATH).run();
    }
}
