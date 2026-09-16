package slowbro;

import java.util.ArrayList;
import java.util.Scanner;

/** Runs the Slowbro task-list application. */
public class SlowBro {
    private static final String DIVIDER =
            "____________________________________________________________";
    private static final int MAX_TASK_COUNT = 100;
    private static final String BYE_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND_PREFIX = "mark ";
    private static final String UNMARK_COMMAND_PREFIX = "unmark ";
    private static final String TODO_COMMAND_PREFIX = "todo";
    private static final String DEADLINE_COMMAND_PREFIX = "deadline";
    private static final String EVENT_COMMAND_PREFIX = "event";
    private static final String DELETE_COMMAND_PREFIX = "delete";
    private static final String BY_SEPARATOR = " /by ";
    private static final String FROM_SEPARATOR = " /from ";
    private static final String TO_SEPARATOR = " /to ";
    private static final int MARK_COMMAND_LENGTH = MARK_COMMAND_PREFIX.length();
    private static final int UNMARK_COMMAND_LENGTH = UNMARK_COMMAND_PREFIX.length();
    private static final int TODO_COMMAND_LENGTH = TODO_COMMAND_PREFIX.length();
    private static final int DEADLINE_COMMAND_LENGTH = DEADLINE_COMMAND_PREFIX.length();
    private static final int EVENT_COMMAND_LENGTH = EVENT_COMMAND_PREFIX.length();
    private static final int FROM_SEPARATOR_LENGTH = FROM_SEPARATOR.length();
    private static final int TO_SEPARATOR_LENGTH = TO_SEPARATOR.length();
    private static final String TASK_LIST_HEADER =
            " Here are the tasks in your list:";
    private static final String TASK_ADDED_HEADER =
            " Got it. I've added this task:";
    private static final String TASK_DELETED_HEADER =
            " Noted. I have removed this task:";
    private static final String TASK_COUNT_FORMAT =
            " Now you have %d tasks in the list.";
    private static final String INVALID_TASK_NUMBER_MESSAGE =
            " Please provide a valid task number.";
    private static final String OUT_OF_BOUNDS_TASK_NUMBER_MESSAGE =
            " Task number out of range.";
    private static final String INVALID_COMMAND_MESSAGE =
            " Sorry, I couldn't understand that command.";
    private static final String INVALID_NUMBER_MESSAGE =
            " Please enter a valid number.";
    private static final String MARKED_DONE_MESSAGE =
            " Nice! I've marked this task as done:";
    private static final String MARKED_NOT_DONE_MESSAGE =
            " OK, I've marked this task as not done yet:";
    private static final String DEADLINE_USAGE_MESSAGE =
            " Usage: deadline <description> /by <date or time>.";
    private static final String EVENT_USAGE_MESSAGE =
            " Usage: event <description> /from <start> /to <end>.";
    private static final String EMPTY_TASK_MESSAGE =
            " Task descriptions and details cannot be empty.";
    private static final String TASK_LIMIT_MESSAGE =
            " You cannot add more than 100 tasks.";
    private static final String EMPTY_COMMAND_MESSAGE =
            " Please enter a command.";
    private static final String COMMAND_USAGE_MESSAGE =
            " Available Commands: todo, deadline, event.";
    private static final String DELETE_USAGE_MESSAGE =
            " Usage: delete <task number>";
    private static final String EXIT_MESSAGE =
            "Bye. Hope to see you again soon!";

    /** Starts the application and processes commands until the user exits. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Storage storage = new Storage();
        ArrayList<Task> loadedTasks = storage.load();
        int taskCount = loadedTasks.size();
        tasks.addAll(loadedTasks);
//        System.arraycopy(loadedTasks, 0, tasks, 0, taskCount);

        printGreeting();

        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine();

            if (input.equals(BYE_COMMAND)) {
                break;
            } else {
                taskCount = handleCommand(input, tasks, taskCount, storage);
            }
        }

        printExitMessage();
        scanner.close();
    }

    private static void printGreeting() {
        String banner = "  ____  _               _                \n"
                + " / ___|| | ___         | |__  _ __ ___  \n"
                + " \\___ \\| |/ _ \\ \\ /\\ / / '_ \\| '__/ _ \\\n"
                + "  ___) | | (_) \\ V  V /| |_) | | | (_) |\n"
                + " |____/|_|\\___/ \\_/\\_/ |_.__/|_|  \\___/\n";

        System.out.println(DIVIDER);
        System.out.print(banner);
        System.out.println("Hello! I'm Slowbro.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }
    private static int handleCommand(String input, ArrayList<Task> tasks, int taskCount, Storage storage) {
        try {
            if (input.trim().isEmpty()) {
                throw new InvalidCommandException(EMPTY_COMMAND_MESSAGE);
            }

            if (input.equals(LIST_COMMAND)) {
                listTasks(tasks, taskCount);
                return taskCount;
            } else if (input.startsWith(MARK_COMMAND_PREFIX)
                    || input.startsWith(UNMARK_COMMAND_PREFIX)) {
                markTask(input, tasks, taskCount, storage);
                return taskCount;
            } else if (input.startsWith(TODO_COMMAND_PREFIX)) {
                if (input.equals(TODO_COMMAND_PREFIX)) {
                    throw new InvalidCommandException(EMPTY_TASK_MESSAGE);
                }
                String description = input.substring(TODO_COMMAND_LENGTH).trim();
                validateTaskFields(description);
                addTask(new Todo(description), tasks, taskCount);
                storage.save(tasks, taskCount + 1);
                return taskCount + 1;
            } else if (input.startsWith(DEADLINE_COMMAND_PREFIX)) {
                String command = input.substring(DEADLINE_COMMAND_LENGTH);
                int byIndex = command.indexOf(BY_SEPARATOR);
                if (byIndex >= 0) {
                    String description = command.substring(0, byIndex).trim();
                    String by = command.substring(byIndex + BY_SEPARATOR.length()).trim();
                    validateTaskFields(description, by);
                    addTask(new Deadline(description, by), tasks, taskCount);
                    storage.save(tasks, taskCount + 1);
                    return taskCount + 1;
                }
                throw new InvalidCommandException(DEADLINE_USAGE_MESSAGE);
            } else if (input.startsWith(EVENT_COMMAND_PREFIX)) {
                String command = input.substring(EVENT_COMMAND_LENGTH);
                int fromIndex = command.indexOf(FROM_SEPARATOR);
                int toIndex = command.indexOf(TO_SEPARATOR);
                if (fromIndex >= 0 && toIndex > fromIndex) {
                    String description = command.substring(0, fromIndex).trim();
                    String from = command.substring(fromIndex + FROM_SEPARATOR_LENGTH, toIndex).trim();
                    String to = command.substring(toIndex + TO_SEPARATOR_LENGTH).trim();
                    validateTaskFields(description, from, to);
                    addTask(new Event(description, from, to), tasks, taskCount);
                    storage.save(tasks, taskCount + 1);
                    return taskCount + 1;
                }
                throw new InvalidCommandException(EVENT_USAGE_MESSAGE);
            } else if (input.startsWith(DELETE_COMMAND_PREFIX)) {
                String[] words = input.split("\\s+");
                if(words.length != 2) {
                    throw new InvalidCommandException(DELETE_USAGE_MESSAGE);
                }
                int index = Integer.parseInt(words[1])-1;
                if(index+1 > taskCount) {
                    throw new IndexOutOfBoundsException();
                }
                System.out.println(DIVIDER);
                System.out.println(TASK_DELETED_HEADER);
                System.out.println("   " + tasks.get(index));
                taskCount -= 1;
                System.out.println(String.format(TASK_COUNT_FORMAT, taskCount));
                System.out.println(DIVIDER);
                tasks.remove(index);
                return taskCount;
            } else {
                throw new InvalidCommandException(COMMAND_USAGE_MESSAGE);
            }
        } catch (InvalidCommandException e) {
            printInvalidCommand(e.getUsageMessage());
        } catch (IllegalStateException e) {
            printInvalidCommand(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println(DIVIDER);
            System.out.println(OUT_OF_BOUNDS_TASK_NUMBER_MESSAGE);
            System.out.println(DIVIDER);
        } catch (NumberFormatException e) {
            System.out.println(DIVIDER);
            System.out.println(INVALID_NUMBER_MESSAGE);
            System.out.println(DIVIDER);
        }
        return taskCount;
    }

    private static void printInvalidCommand(String usageMessage) {
        System.out.println(DIVIDER);
        System.out.println(INVALID_COMMAND_MESSAGE);
        System.out.println(usageMessage);
        System.out.println(DIVIDER);
    }

    private static void listTasks(ArrayList<Task> tasks, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println(TASK_LIST_HEADER);
        for (int i = 0; i < taskCount; i++) {
            System.out.println(String.format(" %d.%s", i + 1, tasks.get(i)));
        }
        System.out.println(DIVIDER);
    }

    private static void markTask(String input, ArrayList<Task> tasks, int taskCount, Storage storage) {
        boolean shouldUnmark = input.startsWith(UNMARK_COMMAND_PREFIX);
        int commandLength = shouldUnmark
                ? UNMARK_COMMAND_LENGTH
                : MARK_COMMAND_LENGTH;
        String indexText = input.substring(commandLength).trim();

        try {
            int index = Integer.parseInt(indexText) - 1;
            if (index < 0 || index >= taskCount) {
                throw new IndexOutOfBoundsException();
            }

            System.out.println(DIVIDER);
            if (shouldUnmark) {
                tasks.get(index).unmarkAsDone();
                System.out.println(MARKED_NOT_DONE_MESSAGE);
            } else {
                tasks.get(index).markAsDone();
                System.out.println(MARKED_DONE_MESSAGE);
            }
            System.out.println(tasks.get(index));
            System.out.println(DIVIDER);
            storage.save(tasks, taskCount);
        } catch (NumberFormatException e) {
            System.out.println(DIVIDER);
            System.out.println(INVALID_TASK_NUMBER_MESSAGE);
            System.out.println(DIVIDER);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(DIVIDER);
            System.out.println(OUT_OF_BOUNDS_TASK_NUMBER_MESSAGE);
            System.out.println(DIVIDER);
        }
    }

    private static void addTask(Task task, ArrayList<Task> tasks, int taskCount) {
        if (taskCount >= MAX_TASK_COUNT) {
            throw new IllegalStateException(TASK_LIMIT_MESSAGE);
        }

        tasks.add(task);
        System.out.println(DIVIDER);
        System.out.println(TASK_ADDED_HEADER);
        System.out.println("   " + task);
        System.out.println(String.format(TASK_COUNT_FORMAT, taskCount + 1));
        System.out.println(DIVIDER);
    }

    private static void validateTaskFields(String... fields) throws InvalidCommandException {
        for (String field : fields) {
            if (field.isEmpty()) {
                throw new InvalidCommandException(EMPTY_TASK_MESSAGE);
            }
        }
    }

    private static void printExitMessage() {
        System.out.println(DIVIDER);
        System.out.println(EXIT_MESSAGE);
        System.out.println(DIVIDER);
    }
}
