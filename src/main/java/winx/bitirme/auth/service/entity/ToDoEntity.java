package winx.bitirme.auth.service.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "to-do")
public class ToDoEntity {
    private String task;
    private boolean isDone;
    private String username;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
