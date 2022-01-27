package winx.bitirme.mongo.client.model;

import winx.bitirme.mongo.service.entity.Answer;

public class AnswerSubmitRequest {
    private Answer[] payload;
    public AnswerSubmitRequest(){

    }
    public Answer[] getPaylaod(){
        return this.payload;
    }
    public void setPayload(Answer[] toSet){
        this.payload = toSet;
    }
}
