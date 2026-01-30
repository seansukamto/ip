import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to/from a file.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage instance with the specified file path.
     *
     * @param filePath Relative path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return List of tasks loaded from file.
     * @throws SejongException If there is an error loading the file.
     */
    public List<Task> loadTasks() throws SejongException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    Task task = parseTask(line);
                    tasks.add(task);
                } catch (Exception e) {
                    // Skip corrupted lines but continue loading
                    System.out.println("Warning: Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            throw new SejongException("Error loading tasks from file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves tasks to the storage file.
     *
     * @param tasks List of tasks to save.
     * @throws SejongException If there is an error saving the file.
     */
    public void saveTasks(List<Task> tasks) throws SejongException {
        try {
            // Create directory if it doesn't exist
            Path path = Paths.get(filePath);
            Path parentDir = path.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            // Write tasks to file
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new SejongException("Error saving tasks to file: " + e.getMessage());
        }
    }

    /**
     * Parses a line from the file into a Task object.
     *
     * @param line Line from the file in format: TYPE | STATUS | DESCRIPTION | [ADDITIONAL]
     * @return Parsed Task object.
     * @throws SejongException If the line format is invalid.
     */
    private Task parseTask(String line) throws SejongException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new SejongException("Invalid task format");
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        switch (type) {
        case "T":
            return new Todo(description, isDone);
        case "D":
            if (parts.length < 4) {
                throw new SejongException("Invalid deadline format");
            }
            String by = parts[3].trim();
            return new Deadline(description, by, isDone);
        case "E":
            if (parts.length < 5) {
                throw new SejongException("Invalid event format");
            }
            String from = parts[3].trim();
            String to = parts[4].trim();
            return new Event(description, from, to, isDone);
        default:
            throw new SejongException("Unknown task type: " + type);
        }
    }
}
