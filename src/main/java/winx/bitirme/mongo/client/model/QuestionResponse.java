package winx.bitirme.mongo.client.model;

import org.apache.commons.math3.ml.clustering.Cluster;
import winx.bitirme.mongo.service.entity.ClusteringQuestion;

import java.util.List;


public class QuestionResponse {
    private List<ClusteringQuestion> questions;
    public QuestionResponse(){

    }
    public List<ClusteringQuestion>  getQuestions(){
        return this.questions;
    }
    public void setQuestions(List<ClusteringQuestion> toSet){
        this.questions = toSet;
    }
    public QuestionResponse(List<ClusteringQuestion>data){
        this.questions = data;
    }
}
