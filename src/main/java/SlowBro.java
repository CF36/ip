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

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            }

            System.out.println(divider);
            System.out.println(input);
            System.out.println(divider);
        }

        // Exit
        System.out.println(divider);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(divider);

        scanner.close();
    }
}
