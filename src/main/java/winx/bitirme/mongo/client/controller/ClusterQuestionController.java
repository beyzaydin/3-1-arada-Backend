package winx.bitirme.mongo.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.mongo.client.model.AddQuestionRequest;
import winx.bitirme.mongo.client.model.AnswerSubmitRequest;
import winx.bitirme.mongo.client.model.DeleteQuestionRequest;
import winx.bitirme.mongo.client.model.QuestionResponse;

import winx.bitirme.mongo.service.entity.*;
import winx.bitirme.mongo.service.logic.ClusteringFormService;
import winx.bitirme.mongo.service.logic.ClusteringQuestionService;
import winx.bitirme.mongo.service.repository.ClusteredFormRepository;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/question")
@CrossOrigin(origins = "http://localhost:3000")
public class ClusterQuestionController {
    final ClusteringQuestionService clusteringQuestionService;
    final ClusteringFormService clusteringFormService;
    final ClusteredFormRepository clusteredFormRepository;
    final UserRepository userRepository;
    final ObjectMapper mapper;
    @Autowired
    public ClusterQuestionController(ClusteringQuestionService clusteringQuestionService, ObjectMapper mapper, ClusteringFormService clusteringFormService, ClusteredFormRepository clusteredFormRepository, UserRepository userRepository) {
        this.clusteringQuestionService = clusteringQuestionService;
        this.clusteringFormService = clusteringFormService;
        this.mapper = mapper;
        this.clusteredFormRepository = clusteredFormRepository;
        this.userRepository = userRepository;
    }
    @GetMapping(value = "/getQuestions", produces ="application/json")
    @ResponseBody
    public QuestionResponse getQuestions() {
        return new QuestionResponse(this.clusteringQuestionService.getAllQuestions());
    }
    @PostMapping(value = "/submitAnswers",consumes="application/json")
    public void submitAnswers(@RequestBody AnswerSubmitRequest payload){
        User submitter = this.userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        ClusteringQuestion iterate;
        if (payload.getPayload().get(0).getSubmitter() == null){
            AnswerSubmitRequest replacer = new AnswerSubmitRequest();
            ArrayList<SubmittedAnswer> answerReplace = new ArrayList<>();
            for (SubmittedAnswer toAdd : payload.getPayload()){
                answerReplace.add(new SubmittedAnswer(submitter,toAdd.getQuestionBody(),toAdd.getAnswer()));
            }
            replacer.setPayload(answerReplace);
            payload = replacer;
        }
        ArrayList<Answer> toInsert = new ArrayList<>();
        if (submitter == null){
            return;
        }
        for (SubmittedAnswer submittedAnswer : payload.getPayload()){
            iterate = this.clusteringQuestionService.getQuestionIfExists(submittedAnswer.getQuestionBody());
            if (iterate != null && submittedAnswer.getAnswer() != null && !submittedAnswer.getAnswer().isEmpty()){
                toInsert.add(new Answer(iterate,submittedAnswer.getAnswer()));
            }
        }
        if (toInsert.size() != 0){
            this.clusteringFormService.submitClusteringForm(new ClusteringForm(submitter,toInsert));
            if (this.clusteringFormService.getSubmittedAnswers().size() > 100){
                List<ClusteredForm> clusteredData = this.clusteringQuestionService.clusterQuestions();
                this.clusteredFormRepository.saveAll(clusteredData);
            }

        }

    }
    @PostMapping(value ="/deleteQuestion", consumes="application/json", produces="application/json")
    public boolean deleteQuestion(@RequestBody DeleteQuestionRequest toRemove){
        return this.clusteringQuestionService.deleteQuestion(toRemove.getQuestionBody());

    }
    @PostMapping(value = "/addQuestion", consumes="application/json", produces="application/json")
    public boolean addQuestion(@RequestBody AddQuestionRequest payload){
        try{
            this.clusteringQuestionService.saveQuestion(payload.constructQuestion());
            return true;
        }
        catch(Exception e){
            return false;
        }

    }
    @GetMapping(value = "/populateDataset")
    public void populateDataset(){
        this.clusteringQuestionService.generateRandomAnswers();
    }
    @GetMapping(value ="/clusterQuestions")
    public void clusterQuestions(){
        List<ClusteredForm> clusteredFormList = this.clusteringQuestionService.clusterQuestions();
        this.clusteredFormRepository.saveAll(clusteredFormList);
    }
    @GetMapping(value="/getClusteredForms")
    public List<ClusteredForm> getClusteredForms(){
        return this.clusteredFormRepository.findAll();
    }

}
