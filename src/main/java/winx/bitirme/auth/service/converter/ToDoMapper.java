package winx.bitirme.auth.service.converter;

import org.mapstruct.Mapper;
import winx.bitirme.auth.client.model.ToDoModel;
import winx.bitirme.auth.service.entity.ToDoEntity;

import java.util.List;

@Mapper
public interface ToDoMapper {
    public ToDoEntity convertToEntity(ToDoModel model);
    public ToDoModel convertToModel(ToDoEntity model);
    public List<ToDoEntity> convertToEntityList(List<ToDoModel> model);
    public List<ToDoModel> convertToModelList(List<ToDoEntity> model);
}
