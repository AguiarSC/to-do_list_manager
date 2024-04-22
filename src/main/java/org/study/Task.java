package org.study;

class Task {
    private String description;
    private boolean marked;

    // Constructor
    public Task(String description) {
        this.description = description;
        this.marked = false;
    }

    // Getter for the task description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMarked() {
        return marked;
    }

    public void mark() {
        marked = true;
    }

    public void unmark() {
        marked = false;
    }

    // toString for the task description
    @Override
    public String toString() {
        return description;
    }

}