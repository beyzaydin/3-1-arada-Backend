package winx.bitirme.mongo.client.model;

import winx.bitirme.mongo.service.entity.ClusteringQuestion;
import winx.bitirme.mongo.service.entity.QuestionType;

public class AddQuestionRequest {
    private String questionBody;
    private String answerType;
    private String[] potentialAnswer;

    public AddQuestionRequest() {
    }

    public AddQuestionRequest(String questionBody, String answerType, String[] potentialAnswer) {
        this.questionBody = questionBody;
        this.answerType = answerType;
        this.potentialAnswer = potentialAnswer;
    }
    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String[] getPotentialAnswer() {
        return potentialAnswer;
    }

    public void setPotentialAnswer(String[] potentialAnswer) {
        this.potentialAnswer = potentialAnswer;
    }
    public ClusteringQuestion constructQuestion(){
        String[] potentialAnswers;
        switch  (answerType){
            case "SCORE":
                potentialAnswers = new String[]{"1", "2", "3", "4", "5"};
                return new ClusteringQuestion(questionBody,QuestionType.SCORE,potentialAnswers);
            case "BOOLEAN":
                potentialAnswers = new String[]{"True", "False"};
                return new ClusteringQuestion(this.questionBody,QuestionType.BOOLEAN,potentialAnswers);
            case "DROPDOWN":
                return new ClusteringQuestion(this.questionBody,QuestionType.BOOLEAN,this.potentialAnswer);
            case "OPEN_ENDED":
                return new ClusteringQuestion(this.questionBody,QuestionType.OPEN_ENDED,null);
            default:
                throw new IllegalStateException("Unexpected value: " + answerType);
        }
    }
}
