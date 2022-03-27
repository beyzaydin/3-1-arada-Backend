package winx.bitirme.todo.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.todo.client.model.AddTaskRequest;
import winx.bitirme.todo.service.entity.Task;
import winx.bitirme.todo.service.entity.TaskCategory;
import winx.bitirme.todo.service.logic.ToDoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
@CrossOrigin("http://localhost:3000")
public class ToDoController {
    private ToDoService toDoService;
    @Autowired
    public ToDoController(ToDoService toDoService){
        this.toDoService = toDoService;
    }
    @GetMapping(value="/getCategories", produces="application/json")
    public List<TaskCategory> getTaskCategories(){
        List<TaskCategory> taskCategoryList = this.toDoService.getTaskCategories();
        if (taskCategoryList.isEmpty()){
            ArrayList<String> toInitialize = new ArrayList<>();
            toInitialize.add("Habit Tasks");
            toInitialize.add("Important Tasks");
            toInitialize.add("Planned");
            toInitialize.add("All Tasks");
            toInitialize.add("Completed");
            return this.toDoService.initializeTaskCategories(SecurityContextHolder.getContext().getAuthentication().getName(),toInitialize);
        }
        return this.toDoService.getTaskCategories();
    }
    @PostMapping(value="/addTask",produces="application/json",consumes="application/json")
    public Task addTask(@RequestBody AddTaskRequest toAdd){
        String submitter = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.toDoService.addTask(submitter,toAdd.getCategoryId(),toAdd.getTaskName());
    }
}
