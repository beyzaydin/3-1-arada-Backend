package winx.bitirme.mongo.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClusteringQuestion {


    @Id
    private String questionBody;
    private QuestionType answerType;
    private String[] potentialAnswer;



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
}
