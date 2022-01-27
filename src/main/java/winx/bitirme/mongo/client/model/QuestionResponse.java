package winx.bitirme.mongo.client.model;

import winx.bitirme.mongo.service.entity.ClusteringQuestion;


public class QuestionResponse {
    private ClusteringQuestion[] questions;
    public QuestionResponse(){

    }
    public ClusteringQuestion[] getQuestions(){
        return this.questions;
    }
    public void setQuestions(ClusteringQuestion[] toSet){
        this.questions = toSet;
    }
    public QuestionResponse(ClusteringQuestion[] data){
        this.questions = data;
    }
}
