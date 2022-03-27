package winx.bitirme.todo.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.todo.service.entity.Task;
import winx.bitirme.todo.service.entity.TaskCategory;
import winx.bitirme.todo.service.repository.TaskCategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {
    private final TaskCategoryRepository taskCategoryRepository;
    private final UserRepository userRepository;
    @Autowired
    public ToDoService(TaskCategoryRepository taskCategoryRepository, UserRepository userRepository){
        this.taskCategoryRepository = taskCategoryRepository;
        this.userRepository = userRepository;
    }
    public List<TaskCategory> getTaskCategories(){
        return this.taskCategoryRepository.findAll();
    }
    public Task addTask(String submitterName, int categoryId, String taskName){
        User submitter = this.userRepository.findByEmail(submitterName);
        if (categoryId == 0 || submitter == null){
            return null;
        }
        Optional<TaskCategory> result = this.taskCategoryRepository.findById(categoryId);
        if (result.isPresent()){
            Task toAdd = new Task(taskName);
            result.get().addTask(toAdd);
            this.taskCategoryRepository.save(result.get());
            return toAdd;
        }
        return null;
    }
    public List<TaskCategory> initializeTaskCategories(String submitterName, List<String> names){

        User submitter = this.userRepository.findByEmail(submitterName);
        if (submitter == null){
            return null;
        }
        List<TaskCategory> result = new ArrayList<>();

        for (String categoryName : names){
            result.add(new TaskCategory(submitter,categoryName,new ArrayList<>()));
        }
        this.taskCategoryRepository.saveAll(result);
        return result;
    }
}
