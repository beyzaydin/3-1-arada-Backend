package winx.bitirme.chat.service.converter;

import org.mapstruct.Mapper;
import winx.bitirme.mongo.client.model.ExampleModel;
import winx.bitirme.mongo.service.entity.ExampleEntity;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    ExampleModel convertToModel(ExampleEntity entity);

    List<ExampleModel> convertToModelList(List<ExampleEntity> entities);

    ExampleEntity convertToEntity(ExampleModel model);

    List<ExampleEntity> convertToEntityList(List<ExampleModel> models);
}



