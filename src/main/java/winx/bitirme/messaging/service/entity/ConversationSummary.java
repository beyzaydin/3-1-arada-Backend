package winx.bitirme.messaging.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import winx.bitirme.auth.service.entity.User;
@Getter
@Setter
@AllArgsConstructor
public class ConversationSummary{
    public User respondent;
    public String visibleMessage;
}
