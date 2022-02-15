package winx.bitirme.messaging.service.entity;


import javax.validation.constraints.NotNull;

public class ConversationId {
    @NotNull
    private long participant1;
    @NotNull
    private long participant2;
    @NotNull
    private long conversationNumber;

    public long getParticipant2() {
        return participant2;
    }

    public void setParticipant2(long participant2) {
        this.participant2 = participant2;
    }

    public long getConversationNumber() {
        return conversationNumber;
    }

    public void setConversationNumber(long conversationNumber) {
        this.conversationNumber = conversationNumber;
    }

    public ConversationId() {
    }

    public ConversationId(long participant1, long participant2, long conversationNumber) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.conversationNumber = conversationNumber;
    }


    public long getParticipant1() {
        return participant1;
    }

    public void setParticipant1(long participant1) {
        this.participant1 = participant1;
    }
}
