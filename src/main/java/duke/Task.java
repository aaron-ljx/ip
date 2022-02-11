package duke;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This is a parent class that serves as the main template for the various kind of tasks
 * stored in Duke.
 */
public class Task {
    protected String description;
    protected LocalDate date;
    protected boolean isDone;

    public Task (String description) {
        this.description = description;
        this.isDone = false;
        this.date = null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }
}
