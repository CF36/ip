package slowbro;

import java.util.ArrayList;

public class UI {
    private static final String DIVIDER =
            "____________________________________________________________";
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

    public static void printGreeting() {
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

    public static void printExitMessage() {
        System.out.println(DIVIDER);
        System.out.println(EXIT_MESSAGE);
        System.out.println(DIVIDER);
    }

    public static void listTasks(ArrayList<Task> tasks, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println(TASK_LIST_HEADER);
        for (int i = 0; i < taskCount; i++) {
            System.out.println(String.format(" %d.%s", i + 1, tasks.get(i)));
        }
        System.out.println(DIVIDER);
    }

    public static void printInvalidCommand(String usageMessage) {
        System.out.println(DIVIDER);
        System.out.println(INVALID_COMMAND_MESSAGE);
        System.out.println(usageMessage);
        System.out.println(DIVIDER);
    }

    public static void printAddTask(Task task, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println(TASK_ADDED_HEADER);
        System.out.println("   " + task);
        System.out.println(String.format(TASK_COUNT_FORMAT, taskCount + 1));
        System.out.println(DIVIDER);
    }

    public static void printDeleteTask(ArrayList<Task> tasks, int taskCount, int index) {
        System.out.println(DIVIDER);
        System.out.println(TASK_DELETED_HEADER);
        System.out.println("   " + tasks.get(index));
        System.out.println(String.format(TASK_COUNT_FORMAT, taskCount - 1));
        System.out.println(DIVIDER);
    }
}
