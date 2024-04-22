package org.study;

import java.util.ArrayList;
import java.util.List;

// Class representing a list of tasks
class TaskList {
    // List to hold tasks
    private final List<Task> tasks;

    // Constructor
    public TaskList() {
        // Initialize an empty list of tasks
        this.tasks = new ArrayList<>();
    }

    // Method to add a new task to the list
    public void addTask(String description) {
        // Create a new Task object with the given description and add it to the list
        Task task = new Task(description);
        tasks.add(task);
    }

    // Method to display tasks in the list
    public void showTask() throws InterruptedException {
        // Iterate through the list of tasks
        for (int i = 0; i < tasks.size(); i++) {
            // Print the task number and description
            System.out.println((i + 1) + ". " + tasks.get(i));
            // Add a delay of 0.8 seconds between displaying each task
            Thread.sleep(800);
        }
    }

    // Getter for the list of tasks
    public List<Task> getTasks() {
        return tasks;
    }
}