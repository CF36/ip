package seedu.slowbro;

/** Represents a task that can be marked as done or not done. */
public class Task {
    private final String description;
    private boolean isDone;

    /** Creates a task with the given description. */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Returns the status icon for this task. */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as not done. */
    public void unmarkAsDone() {
        isDone = false;
    }
}
