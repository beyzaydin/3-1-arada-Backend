package winx.bitirme.messaging.service.entity;


import winx.bitirme.auth.service.entity.User;

public class ConversationSummary{
    public User respondent;
    public String visibleMessage;

    public ConversationSummary(User respondent, String visibleMessage) {
        this.respondent = respondent;
        this.visibleMessage = visibleMessage;
    }

    public ConversationSummary() {
    }

    public User getRespondent() {
        return respondent;
    }

    public void setRespondent(User respondent) {
        this.respondent = respondent;
    }

    public String getVisibleMessage() {
        return visibleMessage;
    }

    public void setVisibleMessage(String visibleMessage) {
        this.visibleMessage = visibleMessage;
    }


}
