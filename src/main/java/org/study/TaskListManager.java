package org.study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// Class managing task lists and their operations
class TaskListManager {
    // Map to hold task lists with their names as keys
    private final Map<String, TaskList> taskList;
    // Scanner for user input
    private final Scanner scanner;
    // Location for saving task list files
    private final String location;

    // Constructor
    public TaskListManager(String location) {
        // Initialize the map, scanner, and location
        this.taskList = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.location = location;
        // Load existing task lists on creation
        loadTaskList();
    }

    // Method to save task lists to files
    private void saveTaskList() {
        for (Map.Entry<String, TaskList> entry : taskList.entrySet()) {
            String listName = entry.getKey();
            TaskList list = entry.getValue();
            List<Task> tasks = list.getTasks();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(location + File.separator + listName + ".txt"))) {
                for (Task task : tasks) {
                    writer.write(task.getDescription());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to load task lists from files
    private void loadTaskList() {
        File[] files = new File(location).listFiles((dir, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                String listName = file.getName().replace(".txt", "");
                TaskList list = new TaskList();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        list.addTask(line);
                    }
                    taskList.put(listName, list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to create new task lists
    public void createTaskList() {
        boolean creatingLists = true;

        while (creatingLists) {
            System.out.println("Enter the name of the list (or type 'done' to finish):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                creatingLists = false;
            } else {
                taskList.put(input, new TaskList());
                System.out.println("Task list '" + input + "' created.");
            }
        }
        saveTaskList();
    }

    // Method to delete task lists
    public void deleteTaskList() {
        boolean deletingLists = true;

        while (deletingLists) {
            System.out.println("Enter the name of the list to delete (or type 'done' to finish):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                deletingLists = false;
            } else {
                if (taskList.containsKey(input)) {
                    File fileToDelete = new File(location + File.separator + input + ".txt");
                    if (fileToDelete.exists()) {
                        boolean deleteResult = fileToDelete.delete();
                        if (deleteResult) {
                            System.out.println("File '" + fileToDelete.getName() + "' localized successfully.");
                        } else {
                            System.out.println("Failed to delete file '" + fileToDelete.getName() + "'.");
                        }
                    }

                    taskList.remove(input);
                    System.out.println("Task list '" + input + "' deleted.");
                } else {
                    System.out.println("Task list '" + input + "' not found.");
                }
            }
        }
        saveTaskList();
    }

    // Method to add tasks to existing lists
    public void addTask() {
        boolean addingTasks = true;

        while (addingTasks) {
            System.out.println("Enter the name of the list (or type 'done' to finish):");
            String listName = scanner.nextLine();

            if (listName.equalsIgnoreCase("done")) {
                addingTasks = false;
            } else {
                TaskList list = taskList.get(listName);
                if (list != null) {
                    boolean addingMoreTasks = true;
                    while (addingMoreTasks) {
                        System.out.println("Enter the task (or type 'done' to stop adding tasks):");
                        String taskDescription = scanner.nextLine();

                        if (taskDescription.equalsIgnoreCase("done")) {
                            addingMoreTasks = false;
                        } else {
                            list.addTask(taskDescription);
                            System.out.println("Task added to list '" + listName + "'.");
                        }
                    }
                } else {
                    System.out.println("Task list not found.");
                }
            }
        }
        saveTaskList();
    }

    // Method to remove tasks from existing lists
    public void removeTask() {
        boolean removingTasks = true;

        while (removingTasks) {
            System.out.println("Enter the name of the list (or type 'done' to finish):");
            String listName = scanner.nextLine();

            if (listName.equalsIgnoreCase("done")) {
                removingTasks = false;
            } else {
                TaskList list = taskList.get(listName);
                if (list != null) {
                    boolean removingMoreTasks = true;
                    while (removingMoreTasks) {
                        System.out.println("Enter the word to search for in the task description (or type 'done' to finish removing tasks from this list):");
                        String keyword = scanner.nextLine();

                        if (keyword.equalsIgnoreCase("done")) {
                            removingMoreTasks = false;
                        } else {
                            int removedCount = removeTasksContaining(list, keyword);
                            System.out.println(removedCount + " tasks containing '" + keyword + "' removed from list '" + listName + "'.");
                        }
                    }
                } else {
                    System.out.println("Task list not found.");
                }
            }
        }
        saveTaskList();
    }

    // Method to remove tasks containing a keyword from a task list
    private int removeTasksContaining(TaskList list, String keyword) {
        int removedCount = 0;
        Iterator<Task> iterator = list.getTasks().iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getDescription().contains(keyword)) {
                iterator.remove();
                removedCount++;
            }
        }
        return removedCount;
    }

    // Method to display tasks from a chosen list
    public void showTasks() throws InterruptedException {
        System.out.println("Enter the name of the list:");
        String listName = scanner.nextLine();
        TaskList list = taskList.get(listName);
        if (list != null) {
            System.out.println("Tasks in list '" + listName + "':");
            list.showTask();
        } else {
            System.out.println("Task list not found.");
        }
    }

    // Method to mark tasks containing a keyword in a chosen list
    public void markTask() {
        System.out.println("Enter the name of the list:");
        String listName = scanner.nextLine();
        TaskList list = taskList.get(listName);
        if (list != null) {
            System.out.println("Enter the keyword to search for in task list:");
            String keyword = scanner.nextLine();
            int markedCount = markTasksContaining(list, keyword);
            System.out.println(markedCount + " tasks containing '" + keyword + "' marked in list '" + listName + "'.");
        } else {
            System.out.println("Task list not found.");
        }
        saveTaskList();
    }

    // Method to mark tasks containing a keyword in a task list
    private int markTasksContaining(TaskList list, String keyword) {
        int markedCount = 0;
        for (Task task : list.getTasks()) {
            if (task.getDescription().contains(keyword) && !task.isMarked()) {
                task.mark();
                String markedDescription = task.getDescription() + "☑️";
                task.setDescription(markedDescription);
                markedCount++;
            }
        }
        return markedCount;
    }

    // Method to unmark tasks containing a keyword in a chosen list
    public void unmarkTask() {
        System.out.println("Enter the name of the list:");
        String listName = scanner.nextLine();
        TaskList list = taskList.get(listName);
        if (list != null) {
            System.out.println("Enter a keyword to search for in task list:");
            String keyword = scanner.nextLine();
            int unmarkedCount = unmarkTasksContaining(list, keyword);
            System.out.println(unmarkedCount + " tasks containing '" + keyword + "' unmarked in list '" + listName + "'.");
        } else {
            System.out.println("Task list not found.");
        }
        saveTaskList();
    }

    // Method to unmark tasks containing a keyword in a task list
    private int unmarkTasksContaining(TaskList list, String keyword) {
        int unmarkedCount = 0;
        for (Task task : list.getTasks()) {
            if (task.getDescription().contains(keyword) && task.isMarked()) {
                task.unmark();
                String unmarkedDescription = task.getDescription().replace("☑️", "");
                task.setDescription(unmarkedDescription);
                unmarkedCount++;
            }
        }
        return unmarkedCount;
    }

    // Method to close scanner and save task lists
    public void closeScanner() {
        scanner.close();
        saveTaskList();
    }
}
