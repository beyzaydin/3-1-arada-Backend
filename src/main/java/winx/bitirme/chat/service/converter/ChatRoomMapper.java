package winx.bitirme.chat.service.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import winx.bitirme.chat.client.model.ChatRoomModel;
import winx.bitirme.chat.service.entity.ChatRoom;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    @Mapping(source = "entity.user1", target = "sender")
    @Mapping(source = "entity.user2", target = "receiver")
    ChatRoomModel convertToModel(ChatRoom entity);

    List<ChatRoomModel> convertToModelList(List<ChatRoom> entities);

    @Mapping(source = "model.sender", target = "user1")
    @Mapping(source = "model.receiver", target = "user2")
    ChatRoom convertToEntity(ChatRoomModel model);

    List<ChatRoom> convertToEntityList(List<ChatRoomModel> models);
}



