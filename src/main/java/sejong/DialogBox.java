package sejong;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A dialog box for the chat window, showing either user or Sejong messages.
 */
public class DialogBox extends HBox {
    private static final int WRAP_WIDTH = 300;
    private static final int H_PADDING = 14;
    private static final int V_PADDING = 10;
    private static final int SPACING = 8;

    private DialogBox(Label label, boolean isUser) {
        label.setWrapText(true);
        label.setMaxWidth(WRAP_WIDTH);
        label.setMinHeight(Region.USE_PREF_SIZE);
        label.setPadding(new Insets(V_PADDING, H_PADDING, V_PADDING, H_PADDING));
        label.setFocusTraversable(false);

        setSpacing(SPACING);
        setAlignment(isUser ? Pos.TOP_RIGHT : Pos.TOP_LEFT);
        setPadding(new Insets(2));

        if (isUser) {
            label.getStyleClass().add("user-dialog");
            getChildren().addAll(new Label(), label);
        } else {
            label.getStyleClass().add("sejong-dialog");
            getChildren().addAll(label, new Label());
        }
    }

    /**
     * Creates a dialog box for the user's message.
     *
     * @param text User message text.
     * @return A DialogBox showing the user message.
     */
    public static DialogBox getUserDialog(String text) {
        Label label = new Label(text);
        return new DialogBox(label, true);
    }

    /**
     * Creates a dialog box for Sejong's response.
     *
     * @param text Bot response text.
     * @return A DialogBox showing the bot message.
     */
    public static DialogBox getSejongDialog(String text) {
        Label label = new Label(text);
        return new DialogBox(label, false);
    }
}
