package winx.bitirme.mongo.client.model;

public class DeleteQuestionRequest {
    private String questionBody;

    public DeleteQuestionRequest(String questionBody) {
        this.questionBody = questionBody;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public DeleteQuestionRequest() {
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }
}
