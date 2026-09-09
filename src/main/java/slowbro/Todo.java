package slowbro;

public class Todo extends Task{
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
        return "[T]" + super.toString();
    }
}
