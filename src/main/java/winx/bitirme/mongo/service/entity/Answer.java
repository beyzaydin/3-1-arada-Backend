package winx.bitirme.mongo.service.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Answer {
    ClusteringQuestion clusteringQuestion;
    String answer;

    public ClusteringQuestion getClusteringQuestion() {
        return clusteringQuestion;
    }

    public void setClusteringQuestion(ClusteringQuestion clusteringQuestion) {
        this.clusteringQuestion = clusteringQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Answer(ClusteringQuestion clusteringQuestion, String answer) {
        this.clusteringQuestion = clusteringQuestion;
        this.answer = answer;
    }
}
