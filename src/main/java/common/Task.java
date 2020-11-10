package common;

import java.io.Serializable;
import java.util.UUID;

public class Task implements Serializable {
    long id;
    String description;
    int priority;

    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return id + " " + description + " (priority is " + priority + ")\n";
    }
}
