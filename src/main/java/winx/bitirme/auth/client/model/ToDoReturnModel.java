package winx.bitirme.auth.client.model;

import java.util.List;

public class ToDoReturnModel {
    private List<ToDoModel> tasks;

    public ToDoReturnModel(List<ToDoModel> tasks) {
        this.tasks = tasks;
    }

    public List<ToDoModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<ToDoModel> tasks) {
        this.tasks = tasks;
    }
}
