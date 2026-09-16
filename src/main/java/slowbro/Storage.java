package slowbro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/** Handles saving and loading tasks from the hard disk. */
public class Storage {
    private static final Path DATA_FILE = Path.of("data", "slowbro.txt");
    private static final String FIELD_SEPARATOR = "|";

    /** Loads all saved tasks, returning an empty array when no file exists. */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(DATA_FILE)) {
            return tasks;
        }
        try {
            List<String> lines = Files.readAllLines(DATA_FILE);
            for (String line : lines) {
                if (!line.isBlank()) {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Warning: Unable to load saved tasks.");
        }
        return tasks;
    }

    /** Saves the given tasks to the hard disk. */
    public void save(ArrayList<Task> tasks, int taskCount) {
        try {
            Files.createDirectories(DATA_FILE.getParent());
            StringBuilder data = new StringBuilder();
            for (Task task : tasks) {
                data.append(serializeTask(task)).append(System.lineSeparator());
            }
            Files.writeString(DATA_FILE, data.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Warning: Unable to save tasks.");
        }
    }

    private String serializeTask(Task task) {
        String type;
        String[] fields;
        if (task instanceof Deadline deadline) {
            type = "D";
            fields = new String[] { deadline.getDescription(), deadline.getBy() };
        } else if (task instanceof Event event) {
            type = "E";
            fields = new String[] { event.getDescription(), event.getFrom(), event.getTo() };
        } else {
            type = "T";
            fields = new String[] { task.getDescription() };
        }
        StringBuilder result = new StringBuilder(type + FIELD_SEPARATOR
                + (task.getStatusIcon().equals("X")) + FIELD_SEPARATOR);
        for (String field : fields) {
            result.append(encode(field)).append(FIELD_SEPARATOR);
        }
        return result.toString();
    }

    private Task parseTask(String line) {
        String[] parts = line.split("\\|");
        Task task;
        switch (parts[0]) {
        case "T":
            task = new Todo(decode(parts[2]));
            break;
        case "D":
            task = new Deadline(decode(parts[2]), decode(parts[3]));
            break;
        case "E":
            task = new Event(decode(parts[2]), decode(parts[3]), decode(parts[4]));
            break;
        default:
            return null;
        }
        if (Boolean.parseBoolean(parts[1])) {
            task.markAsDone();
        }
        return task;
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
