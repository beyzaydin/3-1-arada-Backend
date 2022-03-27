package winx.bitirme.todo.client.model;

import winx.bitirme.todo.service.entity.Task;

public class AddTaskRequest {
    private int categoryId;
    private String taskName;

    public AddTaskRequest() {
    }

    public AddTaskRequest(int categoryName, String taskName) {
        this.categoryId = categoryName;
        this.taskName = taskName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryName(int categoryName) {
        this.categoryId = categoryName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
