package winx.bitirme.mongo.service.entity;

import org.apache.commons.math3.ml.clustering.Clusterable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import winx.bitirme.auth.service.entity.User;

import java.util.Arrays;
import java.util.Objects;
@Document
public class ClusteringForm implements Clusterable {
    @Id
    private int id;
    private User submitter;
    private Answer[] answers;

    public ClusteringForm(User submitter, Answer[] answers){
        this.answers = answers;
        this.submitter = submitter;
        this.id = this.hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusteringForm that = (ClusteringForm) o;
        return id == that.id && Objects.equals(submitter, that.submitter) && Arrays.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(submitter);
        result = 31 * result + Arrays.hashCode(answers);
        return result;
    }

    @Override
    public double[] getPoint() {
        Answer[] answersSubmitted = this.getAnswers();
        double[] result = new double[answersSubmitted.length];
        for (int i = 0; i < answersSubmitted.length; i++){
            result[i] = answersSubmitted[i].clusteringQuestion.enumerateAnswer(answersSubmitted[i].getAnswer());
        }
        return result;
    }
}
