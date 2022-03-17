package winx.bitirme.mongo.service.entity;

import org.springframework.data.annotation.Id;
import winx.bitirme.auth.service.entity.User;

import java.util.Objects;

public class SubmittedAnswer {
    @Id
    private int id;
    private User submitter;
    private String questionBody;
    private String answer;

    public SubmittedAnswer() {
    }

    public SubmittedAnswer( User submitter, String questionBody, String answer) {
        this.id = this.hashCode();
        this.submitter = submitter;
        this.questionBody = questionBody;
        this.answer = answer;
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
        if (this.answer != null && this.questionBody != null && this.submitter != null){
            this.id = this.hashCode();
        }
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
        if (this.answer != null && this.questionBody != null && this.submitter != null){
            this.id = this.hashCode();
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        if (this.answer != null && this.questionBody != null && this.submitter != null){
            this.id = this.hashCode();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmittedAnswer that = (SubmittedAnswer) o;
        return Objects.equals(submitter, that.submitter) && Objects.equals(questionBody, that.questionBody) && Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(submitter, questionBody, answer);
    }
}
