package winx.bitirme.mongo.client.model;

import winx.bitirme.mongo.service.entity.SubmittedAnswer;

import java.util.List;

public class AnswerSubmitRequest {
    private List<SubmittedAnswer> payload;
    public AnswerSubmitRequest(){

    }
    public  List<SubmittedAnswer>getPayload(){
        return this.payload;
    }
    public void setPayload(List<SubmittedAnswer> toSet){
        this.payload = toSet;
    }
}
