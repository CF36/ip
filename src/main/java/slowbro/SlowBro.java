package slowbro;

import java.util.Scanner;

/** Runs the Slowbro task-list application. */
public class SlowBro {

    /** Starts the application and processes commands until the user exits. */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage();
        TaskList tasks = storage.load();

        UI.printGreeting();

        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine();

            if (Parser.isBye(input)) {
                break;
            } else {
                handleCommand(input, tasks, storage);
            }
        }

        UI.printExitMessage();
        scanner.close();
    }

    private static void handleCommand(String input, TaskList tasks, Storage storage) {
        try {
            Parser.Command command = Parser.parse(input);
            if (command.type() == Parser.CommandType.LIST) {
                UI.listTasks(tasks);
                return;
            } else if (command.type() == Parser.CommandType.MARK) {
                markTask(command, tasks, storage);
                return;
            } else if (command.type() == Parser.CommandType.TODO) {
                addTask(new Todo(command.description()), tasks);
                storage.save(tasks);
                return;
            } else if (command.type() == Parser.CommandType.DEADLINE) {
                addTask(new Deadline(command.description(), command.firstDetail()), tasks);
                storage.save(tasks);
                return;
            } else if (command.type() == Parser.CommandType.EVENT) {
                addTask(new Event(command.description(), command.firstDetail(),
                        command.secondDetail()), tasks);
                storage.save(tasks);
                return;
            } else if (command.type() == Parser.CommandType.DELETE) {
                deleteTask(tasks, command.taskIndex());
                storage.save(tasks);
                return;
            }
        } catch (InvalidCommandException e) {
            UI.printInvalidCommand(e.getUsageMessage());
        } catch (IllegalStateException e) {
            UI.printInvalidCommand(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            UI.printIndexOutOfBounds();
        } catch (NumberFormatException e) {
            UI.printInvalidNumber();
        }
    }

    private static void deleteTask(TaskList tasks, int index) {
        Task deletedTask = tasks.get(index);
        tasks.delete(index);
        UI.printDeleteTask(deletedTask, tasks.size());
    }

    private static void markTask(Parser.Command command, TaskList tasks, Storage storage) {

        try {
            if (command.shouldUnmark()) {
                tasks.unmark(command.taskIndex());
            } else {
                tasks.mark(command.taskIndex());
            }
            UI.printMarkTask(tasks, command.shouldUnmark(), command.taskIndex());
            storage.save(tasks);
        } catch (NumberFormatException e) {
            UI.printInvalidTaskNumber();
        } catch (IndexOutOfBoundsException e) {
            UI.printIndexOutOfBounds();
        }
    }

    private static void addTask(Task task, TaskList tasks) {
        tasks.add(task);
        UI.printAddTask(task, tasks.size() - 1);
    }

}
