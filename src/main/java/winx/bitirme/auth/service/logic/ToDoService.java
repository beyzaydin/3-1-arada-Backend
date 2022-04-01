package winx.bitirme.auth.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import winx.bitirme.auth.client.model.ToDoModel;
import winx.bitirme.auth.service.converter.ToDoMapper;
import winx.bitirme.auth.service.entity.ToDoEntity;
import winx.bitirme.auth.service.repository.ToDoRepository;

import java.util.List;

@Service
public class ToDoService {
    private final ToDoRepository repository;
    private final ToDoMapper mapper;

    @Autowired
    public ToDoService(ToDoRepository repository, ToDoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public ToDoModel saveToDoModel(ToDoModel model){
        ToDoEntity entity = repository.findByUsernameAndTask(model.getUsername(), model.getTask());
        if(entity != null)
            return mapper.convertToModel(entity);
        model.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return mapper.convertToModel(repository.save(mapper.convertToEntity(model)));
    }

    public ToDoModel getToDoModel(String taskName, String username){
        return mapper.convertToModel(repository.findByUsernameAndTask(username, taskName));
    }

    public List<ToDoModel> getToDoListByUsername(String username){
        return mapper.convertToModelList(repository.findAllByUsername(username));
    }

    public void deleteTask(String taskName, String username){
        repository.deleteByUsernameAndTask(username, taskName);
    }

    public ToDoModel updateTask(ToDoModel model){
        if(getToDoModel(model.getTask(), model.getUsername()) == null)
            return null;
        return mapper.convertToModel(repository.save(mapper.convertToEntity(model)));
    }
}
