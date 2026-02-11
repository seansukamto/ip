package sejong;

import javafx.application.Application;

/**
 * Launcher for the Sejong chatbot GUI.
 * Required by JavaFX: the Application subclass must not be the same class that contains main(),
 * to avoid issues with JAR packaging and JavaFX runtime.
 */
public class Launcher {
    /**
     * Entry point for the GUI application.
     *
     * @param args Command-line arguments (passed to JavaFX Application).
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
