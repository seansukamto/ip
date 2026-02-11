package sejong;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * Controller for the main chat window.
 * Handles user input and displays user and bot dialog boxes.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Sejong sejong;

    /**
     * Sets the Sejong chatbot instance (called from Main after FXML load).
     *
     * @param sejong The chatbot instance.
     */
    public void setSejong(Sejong sejong) {
        this.sejong = sejong;
    }

    /**
     * Initializes the controller. Scrolls to bottom when new content is added; Enter sends message.
     */
    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        userInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleUserInput();
            }
        });
    }

    /**
     * Shows the welcome message as the first bot dialog.
     */
    public void showWelcome() {
        String welcome = sejong.getResponse("");
        dialogContainer.getChildren().add(DialogBox.getSejongDialog(welcome));
    }

    /**
     * Handles user input: adds user dialog, gets response, adds bot dialog, clears input.
     * If user typed "bye", exits the application after showing the response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }
        userInput.clear();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getSejongDialog(sejong.getResponse(input))
        );

        if (input.equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }
}
