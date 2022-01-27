package winx.bitirme.mongo.service.logic;

import org.springframework.stereotype.Component;
import winx.bitirme.mongo.service.entity.Answer;
import winx.bitirme.mongo.service.repository.AnswerRepository;

import java.util.List;
@Component
public class AnswerService {
    private AnswerRepository answerRepository;
    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }
    public void submitAnswers(List<Answer> toSubmit){
        this.answerRepository.insert(toSubmit);
    }
    public void submitAnswers(Answer[] toSubmit){
        for (Answer submit : toSubmit){
            this.answerRepository.insert(submit);
        }
    }
}
