package winx.bitirme.mongo.service.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Answer {
    ClusteringQuestion question;
    String answer;
    public Answer(){

    }
    public Answer(ClusteringQuestion question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public ClusteringQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ClusteringQuestion question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
