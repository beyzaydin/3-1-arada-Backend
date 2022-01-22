package com.tr.btr.mongo.service.logic;

import com.tr.btr.mongo.client.model.ExampleModel;
import com.tr.btr.mongo.service.entity.ExampleEntity;
import com.tr.btr.mongo.service.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//todo:: mapstruct implement sorunu çözülecek

@Service
public class ExampleServiceImpl implements ExampleService {
    private final ExampleRepository repository;
    //private final ExampleMapper mapper;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoOperations mongo;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ExampleServiceImpl(ExampleRepository repository,
                              //ExampleMapper mapper,
                              SequenceGeneratorService sequenceGeneratorService,
                              MongoOperations mongo,
                              MongoTemplate mongoTemplate) {
        this.repository = repository;
        //this.mapper = mapper;
        //this.mapper = mapper;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.mongo = mongo;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ExampleModel save(ExampleModel model) {
      /*  model.setId(sequenceGeneratorService.generateSequence(ExampleEntity.SEQUENCE_NAME));
        return mapper.convertToModel(repository.save(mapper.convertToEntity(model)));*/
        long id = sequenceGeneratorService.generateSequence(ExampleEntity.SEQUENCE_NAME);
        ExampleEntity entity;
        entity = repository.save(new ExampleEntity(id, model.getName()));
        model.setId(entity.getId());
        return model;
    }

    @Override
    public ExampleModel update(ExampleModel model) throws Exception {
        /*ExampleEntity entity = mongo.findOne(
                Query.query(Criteria.where("id").is(model.getId())), ExampleEntity.class);
        if (entity == null)
            throw new Exception("Record not found");
        entity.setName(model.getName());
        return mapper.convertToModel(repository.save(entity));*/
        ExampleEntity entity = mongo.findOne(
                Query.query(Criteria.where("id").is(model.getId())), ExampleEntity.class);
        if (entity == null)
            throw new Exception("Record not found");
        entity.setName(model.getName());
        repository.save(entity);
        return new ExampleModel(entity.getId(), entity.getName());

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
        //return mapper.convertToModel(entity);
        if(entity == null)
            return null;
        return new ExampleModel(entity.getId(), entity.getName());

    }

    @Override
    public List<ExampleModel> getList() {
        List<ExampleEntity> entities = mongo.findAll(ExampleEntity.class);
        List<ExampleModel> models = new ArrayList<>();
        entities.forEach(element -> {
            models.add(new ExampleModel(element.getId(), element.getName()));
        });
        // return mapper.convertToModelList(mongo.findAll(ExampleEntity.class));
        return models;
    }
}
