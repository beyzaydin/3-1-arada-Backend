package tr.com.rest.btr.mongo.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tr.com.rest.btr.mongo.client.model.ExampleModel;
import tr.com.rest.btr.mongo.service.entity.ExampleEntity;
import tr.com.rest.btr.mongo.service.mapper.ExampleMapper;
import tr.com.rest.btr.mongo.service.repository.ExampleRepository;

import java.util.List;

@Service
public class ExampleServiceImpl implements ExampleService{
    ExampleRepository repository;
    ExampleMapper mapper;
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private MongoOperations mongo;

    @Autowired
    public ExampleServiceImpl(ExampleRepository repository,
                              ExampleMapper mapper,
                              SequenceGeneratorService sequenceGeneratorService) {
        this.repository = repository;
        this.mapper = mapper;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public ExampleModel save(ExampleModel model) {
        model.setId(sequenceGeneratorService.generateSequence(ExampleEntity.SEQUENCE_NAME));
        return mapper.convertToModel(repository.save(mapper.convertToEntity(model)));
    }

    @Override
    public ExampleModel update(ExampleModel model) throws Exception {
        ExampleEntity entity = mongo.findOne(
                Query.query(Criteria.where("id").is(model.getId())), ExampleEntity.class);
        if (entity == null)
            throw new Exception("Record not found");
        entity.setName(model.getName());
        return mapper.convertToModel(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) throws Exception {
        ExampleEntity entity = mongo.findOne(
                Query.query(Criteria.where("id").is(id)), ExampleEntity.class);
        if (entity == null)
            throw new Exception("Record not found");
        repository.delete(entity);
    }

    @Override
    public ExampleModel getById(Long id) {
        ExampleEntity entity = mongo.findOne(
                Query.query(Criteria.where("id").is(id)), ExampleEntity.class);
        return mapper.convertToModel(entity);
    }

    @Override
    public List<ExampleModel> getList() {
        return mapper.convertToModelList(mongo.findAll(ExampleEntity.class));
    }
}
