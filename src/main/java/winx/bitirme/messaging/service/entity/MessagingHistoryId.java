package winx.bitirme.messaging.service.entity;


public class MessagingHistoryId {

    private long participant1;
    private long participant2;

    public MessagingHistoryId(long participant1, long participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
    }

    public long getParticipant1() {
        return participant1;
    }

    public void setParticipant1(long participant1) {
        this.participant1 = participant1;
    }

    public long getParticipant2() {
        return participant2;
    }

    public void setParticipant2(long participant2) {
        this.participant2 = participant2;
    }
}
