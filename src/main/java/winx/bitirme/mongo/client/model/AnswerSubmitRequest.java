package winx.bitirme.mongo.client.model;

import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.entity.VisibleUser;
import winx.bitirme.mongo.service.entity.Answer;
import winx.bitirme.mongo.service.entity.SubmittedAnswer;

public class AnswerSubmitRequest {


    private VisibleUser user;
    private SubmittedAnswer[] payload;
    public AnswerSubmitRequest(){

    }
    public VisibleUser getUser() {
        return user;
    }

    public void setUser(VisibleUser user) {
        this.user = user;
    }
    public SubmittedAnswer[] getPayload(){
        return this.payload;
    }
    public void setPayload(SubmittedAnswer[] toSet){
        this.payload = toSet;
    }
}
