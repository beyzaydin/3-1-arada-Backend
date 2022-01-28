package winx.bitirme.mongo.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ClusteringQuestion {
    //todo bunlari enum yapalim
    public static String QUESTION_TYPE_SCORE = "score";
    public static String QUESTION_TYPE_OPEN_ENDED = "openEnd";
    public static String QUESTION_TYPE_DROPDOWN = "dropDown";
    public static String QUESTION_TYPE_BOOLEAN = "boolean";
    @Id
    private String questionBody;
    private String answerType;
    private String[] potentialAnswer;

    public ClusteringQuestion() {

    }

    public ClusteringQuestion(String questionBody, String answerType, String[] potentialAnswers) {
        this.questionBody = questionBody;
        this.answerType = answerType;
        this.potentialAnswer = potentialAnswers;
    }

    //case when question type is "score"
    public ClusteringQuestion(String questionBody, String answerType) {
        if (answerType.equals(ClusteringQuestion.QUESTION_TYPE_SCORE)) {
            this.questionBody = questionBody;
            this.answerType = answerType;
            String[] possibleAnswer = {"1", "2", "3", "4", "5"};
            this.potentialAnswer = possibleAnswer;
        } else if (answerType.equals(ClusteringQuestion.QUESTION_TYPE_BOOLEAN)) {
            this.questionBody = questionBody;
            this.answerType = answerType;
            String[] possibleAnswer = {"True", "False"};
            this.potentialAnswer = possibleAnswer;
        } else if (answerType.equals(ClusteringQuestion.QUESTION_TYPE_OPEN_ENDED)) {
            this.questionBody = questionBody;
            this.answerType = answerType;
        } else throw new IllegalArgumentException("Invalid construction parameters for ClusterinQuestion");

    }

    public String getQuestionBody() {
        return this.questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public String getAnswerType() {
        return this.answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String[] getPotentialAnswer() {
        return this.potentialAnswer;
    }

    public void setPotentialAnswer(String[] potentialAnswer) {
        this.potentialAnswer = potentialAnswer;
    }
}
