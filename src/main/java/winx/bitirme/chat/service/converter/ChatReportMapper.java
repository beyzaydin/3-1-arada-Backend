package winx.bitirme.chat.service.converter;

import org.mapstruct.Mapper;
import winx.bitirme.chat.client.model.ChatReportModel;
import winx.bitirme.chat.service.entity.ChatReportEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatReportMapper {
    ChatReportModel convertToModel(ChatReportEntity entity);
    List<ChatReportModel> convertToModelList(List<ChatReportEntity> entity);

    ChatReportEntity convertToEntity(ChatReportModel model);
    List<ChatReportEntity> convertToEntityList(List<ChatReportModel> model);
}
