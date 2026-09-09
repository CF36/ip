package slowbro;

import java.util.Scanner;

/** Runs the Slowbro task-list application. */
public class SlowBro {
    /** Starts the application and processes commands until the user exits. */
    public static void main(String[] args) {
        String divider = "____________________________________________________________";

        String banner = "  ____  _               _                \n"
                + " / ___|| | ___         | |__  _ __ ___  \n"
                + " \\___ \\| |/ _ \\ \\ /\\ / / '_ \\| '__/ _ \\\n"
                + "  ___) | | (_) \\ V  V /| |_) | | | (_) |\n"
                + " |____/|_|\\___/ \\_/\\_/ |_.__/|_|  \\___/\n";

        // Greeting
        System.out.println(divider);
        System.out.print(banner);
        System.out.println("Hello! I'm Slowbro.");
        System.out.println("What can I do for you?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println(divider);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
                System.out.println(divider);
            } else if (input.startsWith("mark ") || input.startsWith("unmark ")) {
                boolean shouldUnmark = input.startsWith("unmark ");
                String indexText = input.substring(shouldUnmark ? 7 : 5).trim();
                try {
                    int index = Integer.parseInt(indexText) - 1;
                    if (index < 0 || index >= taskCount) {
                        throw new NumberFormatException();
                    }
                    System.out.println(divider);
                    if (shouldUnmark) {
                        tasks[index].unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                    } else {
                        tasks[index].markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                    }
                    System.out.println(tasks[index]);
                    System.out.println(divider);
                } catch (NumberFormatException e) {
                    System.out.println(divider);
                    System.out.println(" Please provide a valid task number.");
                    System.out.println(divider);
                }
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                tasks[taskCount] = new Todo(description);
                taskCount++;

                System.out.println(divider);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(divider);
            } else if (input.startsWith("deadline ")) {
                String command = input.substring(9);
                int byIndex = command.indexOf(" /by ");

                if (byIndex >= 0) {
                    String description = command.substring(0, byIndex).trim();
                    String by = command.substring(byIndex + 5).trim();
                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;

                    System.out.println(divider);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println(divider);
                }
            } else if (input.startsWith("event ")) {
                String command = input.substring(6);
                int fromIndex = command.indexOf(" /from ");
                int toIndex = command.indexOf(" /to ");

                if (fromIndex >= 0 && toIndex > fromIndex) {
                    String description = command.substring(0, fromIndex).trim();
                    String from = command.substring(fromIndex + 7, toIndex).trim();
                    String to = command.substring(toIndex + 5).trim();
                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;

                    System.out.println(divider);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println(divider);
                }
            } else {
                tasks[taskCount] = new Todo(input);
                taskCount++;
                System.out.println(divider);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(divider);
            }
        }

        // Exit
        System.out.println(divider);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(divider);

        scanner.close();
    }
}
