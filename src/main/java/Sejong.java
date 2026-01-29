/**
 * Entry point for the Sejong chatbot.
 */
public class Sejong {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            System.out.println(line);
            System.out.println(" Hello! I'm Sejong");
            System.out.println(" What can I do for you?");
            System.out.println(line);

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if ("bye".equals(input)) {
                    System.out.println(line);
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println(line);
                    break;
                }

                System.out.println(line);
                System.out.println(" " + input);
                System.out.println(line);
            }
        }
    }
}
