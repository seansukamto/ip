package sejong;

import sejong.command.Command;
import sejong.task.TaskList;

/**
 * Entry point for the Sejong chatbot.
 */
public class Sejong {
    private static final String FILE_PATH = "./data/sejong.txt";
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a new Sejong chatbot instance.
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
     * Main entry point.
     */
    public static void main(String[] args) {
        new Sejong(FILE_PATH).run();
    }

}
