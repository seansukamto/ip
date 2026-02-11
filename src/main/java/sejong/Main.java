package sejong;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main application window for the Sejong chatbot GUI.
 * Loads the main layout from FXML and wires the controller to the Sejong instance.
 */
public class Main extends Application {
    private static final String FILE_PATH = "./data/sejong.txt";

    @Override
    public void start(Stage stage) {
        try {
            StringBuilder responseBuffer = new StringBuilder();
            Sejong sejong = new Sejong(FILE_PATH, responseBuffer);

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = loader.load();
            MainWindow mainWindow = loader.getController();
            mainWindow.setSejong(sejong);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/view/application.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Sejong");
            stage.setMinWidth(400);
            stage.setMinHeight(400);
            stage.show();
            stage.toFront();
            stage.requestFocus();

            mainWindow.showWelcome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
