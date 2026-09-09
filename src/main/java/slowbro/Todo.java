package slowbro;

public class Todo extends Task{
    private static final String TODO_PREFIX = "[T]";

    /**
     * Creates a task with the given description.
     *
     * @param description
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return TODO_PREFIX + super.toString();
    }
}
