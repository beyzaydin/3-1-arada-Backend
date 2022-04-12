package winx.bitirme.auth.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import winx.bitirme.auth.client.model.ToDoModel;
import winx.bitirme.auth.client.model.ToDoReturnModel;
import winx.bitirme.auth.service.converter.ToDoMapper;
import winx.bitirme.auth.service.entity.ToDoEntity;
import winx.bitirme.auth.service.repository.ToDoRepository;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;

@Service
public class ToDoService {
    private final ToDoRepository repository;
    private final ToDoMapper mapper;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public ToDoService(ToDoRepository repository, ToDoMapper mapper,
                       SequenceGeneratorService sequenceGeneratorService) {
        this.repository = repository;
        this.mapper = mapper;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }


    public ToDoModel saveToDoModel(ToDoModel model) {
        long id = sequenceGeneratorService.generateSequence(ToDoEntity.SEQUENCE_NAME);
        ToDoEntity entity = repository.findByUsernameAndTask(model.getUsername(), model.getTask());
        if (entity != null)
            return mapper.convertToModel(entity);
        model.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.setId(id);
        return mapper.convertToModel(repository.save(mapper.convertToEntity(model)));
    }

    public ToDoModel getToDoModel(String taskName, String username) {
        return mapper.convertToModel(repository.findByUsernameAndTask(username, taskName));
    }

    public ToDoReturnModel getToDoListByUsername(String username) {
        return new ToDoReturnModel(mapper.convertToModelList(repository.findAllByUsername(username)));
    }

    public void deleteTask(String taskName, String username) {
        repository.deleteByUsernameAndTask(username, taskName);
    }

    public ToDoModel updateTask(ToDoModel model) {
        ToDoEntity entity = repository.findByUsernameAndTask(model.getUsername(), model.getTask());
        if (getToDoModel(model.getTask(), model.getUsername()) == null
            && entity != null )
            return null;
        return mapper.convertToModel(repository.save(mapper.convertToEntity(model)));
    }
}
