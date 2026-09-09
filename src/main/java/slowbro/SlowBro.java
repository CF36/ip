package slowbro;

import java.util.Scanner;

/** Runs the Slowbro task-list application. */
public class SlowBro {
    private static final String DIVIDER =
            "____________________________________________________________";
    private static final int MAX_TASK_COUNT = 100;

    /** Starts the application and processes commands until the user exits. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASK_COUNT];
        int taskCount = 0;

        printGreeting();

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            } else {
                taskCount = handleCommand(input, tasks, taskCount);
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

    private static int handleCommand(String input, Task[] tasks, int taskCount) {
        if (input.equals("list")) {
            listTasks(tasks, taskCount);
            return taskCount;
        }

        if (input.startsWith("mark ") || input.startsWith("unmark ")) {
            markTask(input, tasks, taskCount);
            return taskCount;
        }

        if (input.startsWith("todo ")) {
            addTask(new Todo(input.substring(5).trim()), tasks, taskCount);
            return taskCount + 1;
        }

        if (input.startsWith("deadline ")) {
            String command = input.substring(9);
            int byIndex = command.indexOf(" /by ");
            if (byIndex >= 0) {
                String description = command.substring(0, byIndex).trim();
                String by = command.substring(byIndex + 5).trim();
                addTask(new Deadline(description, by), tasks, taskCount);
                return taskCount + 1;
            }

            printInvalidCommand(
                    " Usage: deadline <description> /by <date or time>.");
            return taskCount;
        }

        if (input.startsWith("event ")) {
            String command = input.substring(6);
            int fromIndex = command.indexOf(" /from ");
            int toIndex = command.indexOf(" /to ");
            if (fromIndex >= 0 && toIndex > fromIndex) {
                String description = command.substring(0, fromIndex).trim();
                String from = command.substring(fromIndex + 7, toIndex).trim();
                String to = command.substring(toIndex + 5).trim();
                addTask(new Event(description, from, to), tasks, taskCount);
                return taskCount + 1;
            }

            printInvalidCommand(
                    " Usage: event <description> /from <start> /to <end>.");
            return taskCount;
        }

        addTask(new Todo(input), tasks, taskCount);
        return taskCount + 1;
    }

    private static void printInvalidCommand(String usageMessage) {
        System.out.println(DIVIDER);
        System.out.println(" Sorry, I couldn't understand that command.");
        System.out.println(usageMessage);
        System.out.println(DIVIDER);
    }

    private static void listTasks(Task[] tasks, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i]);
        }
        System.out.println(DIVIDER);
    }

    private static void markTask(String input, Task[] tasks, int taskCount) {
        boolean shouldUnmark = input.startsWith("unmark ");
        String indexText = input.substring(shouldUnmark ? 7 : 5).trim();

        try {
            int index = Integer.parseInt(indexText) - 1;
            if (index < 0 || index >= taskCount) {
                throw new NumberFormatException();
            }

            System.out.println(DIVIDER);
            if (shouldUnmark) {
                tasks[index].unmarkAsDone();
                System.out.println(" OK, I've marked this task as not done yet:");
            } else {
                tasks[index].markAsDone();
                System.out.println(" Nice! I've marked this task as done:");
            }
            System.out.println(tasks[index]);
            System.out.println(DIVIDER);
        } catch (NumberFormatException e) {
            System.out.println(DIVIDER);
            System.out.println(" Please provide a valid task number.");
            System.out.println(DIVIDER);
        }
    }

    private static void addTask(Task task, Task[] tasks, int taskCount) {
        tasks[taskCount] = task;
        System.out.println(DIVIDER);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + (taskCount + 1) + " tasks in the list.");
        System.out.println(DIVIDER);
    }

    private static void printExitMessage() {
        System.out.println(DIVIDER);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER);
    }
}
