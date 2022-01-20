package tr.com.rest.btr.mongo.service.mapper;

import org.mapstruct.Mapper;
import tr.com.rest.btr.mongo.client.model.ExampleModel;
import tr.com.rest.btr.mongo.service.entity.ExampleEntity;

import java.util.List;

@Mapper
public interface ExampleMapper {
    ExampleModel convertToModel(ExampleEntity entity);
    List<ExampleModel> convertToModelList(List<ExampleEntity> entities);
    ExampleEntity convertToEntity(ExampleModel model);
    List<ExampleEntity> convertToEntityList(List<ExampleModel> models);
}
