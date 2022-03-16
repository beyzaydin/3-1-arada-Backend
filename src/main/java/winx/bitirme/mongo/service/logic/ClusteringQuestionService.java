package winx.bitirme.mongo.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import winx.bitirme.mongo.service.entity.ClusteringQuestion;
import winx.bitirme.mongo.service.entity.QuestionType;
import winx.bitirme.mongo.service.repository.ClusteringQuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClusteringQuestionService {
    ClusteringQuestionRepository clusteringQuestionRepository;

    public ClusteringQuestionService() {
    }

    @Autowired
    public ClusteringQuestionService( ClusteringQuestionRepository clusteringQuestionRepository){
        this.clusteringQuestionRepository = clusteringQuestionRepository;
        //Since questions are uncertain in the development process,
        //some dummy questions are constructed just to verify functionality
        if (this.clusteringQuestionRepository.findAll().isEmpty()){
            String[] dropDown = {"Always","Frequently","infrequently","Rarely","Never"};
            List<ClusteringQuestion> toInsertInitially = new ArrayList<>();
            toInsertInitially.add(new ClusteringQuestion("If you were to score meaning in life, what would it be?", QuestionType.SCORE));
            toInsertInitially.add(new ClusteringQuestion("If you were to score your success in life, what would it be?",QuestionType.SCORE));
            toInsertInitially.add(new ClusteringQuestion("Do you ever feel that you’ve been affected by feelings of edginess, anxiety, or nerves?",QuestionType.BOOLEAN));
            toInsertInitially.add(new ClusteringQuestion("How frequently have you been bothered by not being able to stop worrying?",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("Tell me about how confident you have been feeling in your capabilities recently.", QuestionType.OPEN_ENDED));
            this.clusteringQuestionRepository.insert(toInsertInitially);
        }
    }
    public ClusteringQuestion[] getAllQuestions(){
        List<ClusteringQuestion> query =this.clusteringQuestionRepository.findAll();
        ClusteringQuestion[] result = new ClusteringQuestion[query.size()];
        for (int i = 0; i < query.size(); i++){
            result[i] = query.get(i);
        }
        return result;
    }
    public boolean deleteQuestion(String questionBody){
        Optional<ClusteringQuestion> question = this.clusteringQuestionRepository.findById(questionBody);
        if (question.isPresent()){
            this.clusteringQuestionRepository.delete(question.get());
            return true;
        }
        else return false;
    }
    public void saveQuestion(ClusteringQuestion toSave){
        this.clusteringQuestionRepository.save(toSave);
    }
}
