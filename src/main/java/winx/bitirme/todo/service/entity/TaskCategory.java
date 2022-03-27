package winx.bitirme.todo.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import winx.bitirme.auth.service.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class TaskCategory {
    @Id
    private int id;
    private User submitter;
    private String name;
    List<Task> content;
    public TaskCategory(){

    }
    public TaskCategory(User submitter ,String name, List<Task> content) {
        this.submitter = submitter;
        this.name = name;
        this.content = content;
        this.id = this.hashCode();
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    public void addTask(Task toAdd){
        this.content.add(toAdd);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getContent() {
        return content;
    }

    public void setContent(List<Task> content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskCategory that = (TaskCategory) o;
        return Objects.equals(submitter, that.submitter) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(submitter, name);
    }
}
