package winx.bitirme.messaging.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
public class ConversationId {
    @NotNull
    private long participant1;
    @NotNull
    private long participant2;
    @NotNull
    private long conversationNumber;
}
