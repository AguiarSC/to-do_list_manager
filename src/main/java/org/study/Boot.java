package com.example;

import java.util.Scanner;
// Boot class
public class Main {

    public static void main(String[] args) throws InterruptedException {

        // HERE GOES THE PATH
        // Get user's home directory
        String userHome = System.getProperty("user.home");
        String location = userHome + "/Desktop";
        // Create the path to the desktop directory
        // Default location for saving and loading task lists.
        // You should change "desktopPath". Change lines 9 and 10 with:
        // String location = "your/path"
        // For example: String location = ""C:\workspace\personal""
        // Initialize a TaskListManager with the chosen location
        TaskListManager manager = new TaskListManager(location);
        // Flag to control the loop
        boolean exit = false;

        // Main menu loop
        while (!exit) {
            // Print the main menu
            System.out.println("┌──────────────────────────┐");
            Utils.printCentered(Utils.BLUE + "  SELECT AN OPTION:" + Utils.RESET, 32);
            System.out.println("├──────────────────────────┤");
            Utils.printCentered(Utils.GREEN + "1. " + Utils.RESET + "Create task list", 32);
            Utils.printCentered(Utils.GREEN + "2. " + Utils.RESET + "Add task to list", 32);
            Utils.printCentered(Utils.GREEN + "3. " + Utils.RESET + "Show tasks in list", 32);
            Utils.printCentered(Utils.GREEN + "4. " + Utils.RESET + "Mark as complete", 32);
            System.out.println("│                          │");
            Utils.printCentered(Utils.RED + "5. " + Utils.RESET + "Delete a task", 32);
            Utils.printCentered(Utils.RED + "6. " + Utils.RESET + "Delete task list", 32);
            Utils.printCentered(Utils.RED + "7. " + Utils.RESET + "Unmark a task", 32);
            System.out.println("│                          │");
            Utils.printCentered(Utils.BLUE + "8. EXIT" + Utils.RESET, 32);
            System.out.println("└──────────────────────────┘");

            // Read user input
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            switch (option) {
                // Execute corresponding action based on user input
                case 1:
                    manager.createTaskList();
                    break;
                case 2:
                    manager.addTask();
                    break;
                case 3:
                    manager.showTasks();
                    break;
                case 4:
                    manager.markTask();
                    break;
                case 5:
                    manager.removeTask();
                    break;
                case 6:
                    manager.deleteTaskList();
                    break;
                case 7:
                    manager.unmarkTask();
                    break;
                case 8:
                    // Close scanner and exit the loop
                    manager.closeScanner();
                    exit = true;
                    break;
                default:
                    // Display message for invalid option
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
