import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Sejong chatbot.
 */
public class Sejong {
    private static final String LINE = "____________________________________________________________";

    /**
     * Runs the chatbot loop, storing user inputs and listing them on request.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        List<String> storedInputs = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(LINE);
            System.out.println(" Hello! I'm Sejong");
            System.out.println(" What can I do for you?");
            System.out.println(LINE);

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if ("bye".equals(input)) {
                    System.out.println(LINE);
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println(LINE);
                    break;
                }

                if ("list".equals(input)) {
                    System.out.println(LINE);
                    for (int i = 0; i < storedInputs.size(); i++) {
                        System.out.println(" " + (i + 1) + ". " + storedInputs.get(i));
                    }
                    System.out.println(LINE);
                    continue;
                }

                storedInputs.add(input);
                System.out.println(LINE);
                System.out.println(" added: " + input);
                System.out.println(LINE);
            }
        }
    }
}
