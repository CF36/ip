import java.util.Scanner;

public class SlowBro {
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
                    System.out.println(" " + (i + 1) + ".[" + tasks[i].getStatusIcon()
                            + "] " + tasks[i].getDescription());
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
                    if (shouldUnmark) {
                        tasks[index].unmarkAsDone();
                    } else {
                        tasks[index].markAsDone();
                    }
                    System.out.println(divider);
                    if (shouldUnmark) {
                        System.out.println(" OK, I've marked this task as not done yet:");
                    } else {
                        System.out.println(" Nice! I've marked this task as done:");
                    }
                    System.out.println("   [" + tasks[index].getStatusIcon() + "] "
                            + tasks[index].getDescription());
                    System.out.println(divider);
                } catch (NumberFormatException e) {
                    System.out.println(divider);
                    System.out.println(" Please provide a valid task number.");
                    System.out.println(divider);
                }
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(divider);
                System.out.println(" added: " + input);
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

