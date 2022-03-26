package winx.bitirme.mongo.service.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ClusteringQuestion {


    @Id
    private String questionBody;
    private QuestionType answerType;
    private String[] potentialAnswer;

    public ClusteringQuestion() {
    }

    public ClusteringQuestion(String questionBody, QuestionType answerType, String[] potentialAnswers) {
        this.questionBody = questionBody;
        this.answerType = answerType;
        this.potentialAnswer = potentialAnswers;
    }

    //case when question type is "score"
    public ClusteringQuestion(String questionBody, QuestionType answerType) {
        if (answerType.equals(QuestionType.SCORE)) {
            this.questionBody = questionBody;
            this.answerType = answerType;
            String[] possibleAnswer = {"1", "2", "3", "4", "5"};
            this.potentialAnswer = possibleAnswer;
        } else if (answerType.equals(QuestionType.BOOLEAN)) {
            this.questionBody = questionBody;
            this.answerType = answerType;
            String[] possibleAnswer = {"True", "False"};
            this.potentialAnswer = possibleAnswer;
        } else if (answerType.equals(QuestionType.OPEN_ENDED)) {
            this.questionBody = questionBody;
            this.answerType = answerType;
        } else throw new IllegalArgumentException("Invalid construction parameters for ClusterinQuestion");

    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public QuestionType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(QuestionType answerType) {
        this.answerType = answerType;
    }

    public String[] getPotentialAnswer() {
        return potentialAnswer;
    }

    public void setPotentialAnswer(String[] potentialAnswer) {
        this.potentialAnswer = potentialAnswer;
    }
    public double enumerateAnswer(String answer){
        if (this.potentialAnswer == null){
            return -1;
        }
        for (int i = 0; i < this.potentialAnswer.length; i++){
            if (answer.equals(this.potentialAnswer[i])){
                return i;
            }
        }
        return -1;
    }
}
