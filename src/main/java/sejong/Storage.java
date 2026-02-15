package sejong;

import sejong.task.Deadline;
import sejong.task.Event;
import sejong.task.Task;
import sejong.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to/from a file.
 */
public class Storage {
    /** Delimiter used in file format. */
    private static final String DELIMITER = " | ";
    /** Placeholder for pipe characters inside task descriptions to avoid delimiter collision. */
    private static final String PIPE_PLACEHOLDER = "<<<PIPE>>>";
    
    private final String filePath;

    /**
     * Creates a Storage instance with the specified file path.
     *
     * @param filePath Relative path to the storage file.
     */
    public Storage(String filePath) {
        assert filePath != null : "File path should not be null";
        assert !filePath.isEmpty() : "File path should not be empty";
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
                    assert task != null : "Parsed task should not be null";
                    tasks.add(task);
                } catch (Exception e) {
                    // Skip corrupted lines but continue loading
                    // Note: Errors are silently ignored to allow graceful recovery from corrupted data
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
        assert tasks != null : "Task list should not be null";
        try {
            // Create directory if it doesn't exist
            Path path = Paths.get(filePath);
            Path parentDir = path.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            // Write tasks to file using try-with-resources for proper resource management
            try (FileWriter writer = new FileWriter(filePath)) {
                for (Task task : tasks) {
                    writer.write(taskToLine(task) + System.lineSeparator());
                }
            }
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
        assert line != null : "Line should not be null";
        assert !line.isEmpty() : "Line should not be empty";
        String[] parts = line.split("\\s\\|\\s");
        if (parts.length < 3) {
            throw new SejongException("Invalid task format");
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = unescapePipes(parts[2].trim());
        assert !description.isEmpty() : "Description should not be empty";

        switch (type) {
        case "T":
            return new Todo(description, isDone);
        case "D":
            if (parts.length < 4) {
                throw new SejongException("Invalid deadline format");
            }
            String byStr = parts[3].trim();
            try {
                LocalDate by = LocalDate.parse(byStr);
                return new Deadline(description, by, isDone);
            } catch (Exception e) {
                throw new SejongException("Invalid deadline date format in storage: " + byStr);
            }
        case "E":
            if (parts.length < 5) {
                throw new SejongException("Invalid event format");
            }
            String fromStr = parts[3].trim();
            String toStr = parts[4].trim();
            try {
                LocalDate from = LocalDate.parse(fromStr);
                LocalDate to = LocalDate.parse(toStr);
                return new Event(description, from, to, isDone);
            } catch (Exception e) {
                throw new SejongException("Invalid event date format in storage");
            }
        default:
            throw new SejongException("Unknown task type: " + type);
        }
    }

    /**
     * Serializes a task to a storage line, escaping the description to prevent
     * delimiter collision with pipe characters in the description text.
     *
     * @param task Task to serialize.
     * @return Storage-formatted line with escaped description.
     */
    private String taskToLine(Task task) {
        String desc = escapePipes(task.getDescription());
        String status = task.isDone() ? "1" : "0";

        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D" + DELIMITER + status + DELIMITER + desc + DELIMITER + d.getBy();
        }
        if (task instanceof Event) {
            Event e = (Event) task;
            return "E" + DELIMITER + status + DELIMITER + desc + DELIMITER + e.getFrom() + DELIMITER + e.getTo();
        }
        // Todo (or any future plain-text task)
        return "T" + DELIMITER + status + DELIMITER + desc;
    }

    /**
     * Escapes pipe characters in a string to prevent delimiter collision in storage.
     *
     * @param text Text to escape.
     * @return Text with pipe characters replaced by the placeholder.
     */
    private static String escapePipes(String text) {
        return text.replace("|", PIPE_PLACEHOLDER);
    }

    /**
     * Unescapes pipe placeholders back to pipe characters after loading from storage.
     *
     * @param text Text with placeholders.
     * @return Text with original pipe characters restored.
     */
    private static String unescapePipes(String text) {
        return text.replace(PIPE_PLACEHOLDER, "|");
    }
}
