package winx.bitirme.mongo.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import winx.bitirme.auth.service.entity.User;
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
public class ClusterQuestionController {
    final ClusteringQuestionService clusteringQuestionService;
    final ClusteringFormService clusteringFormService;
    final ClusteredFormRepository clusteredFormRepository;
    final ObjectMapper mapper;
    @Autowired
    public ClusterQuestionController(ClusteringQuestionService clusteringQuestionService, ObjectMapper mapper, ClusteringFormService clusteringFormService, ClusteredFormRepository clusteredFormRepository) {
        this.clusteringQuestionService = clusteringQuestionService;
        this.clusteringFormService = clusteringFormService;
        this.mapper = mapper;
        this.clusteredFormRepository = clusteredFormRepository;
    }
    @GetMapping(value = "/getQuestions", produces ="application/json")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public QuestionResponse getQuestions() {
        return new QuestionResponse(this.clusteringQuestionService.getAllQuestions());
    }
    @PostMapping(value = "/submitAnswers",consumes="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public void submitAnswers(@RequestBody AnswerSubmitRequest payload){
        ClusteringQuestion iterate;
        ArrayList<Answer> toInsert = new ArrayList<>();
        User submitter = this.clusteringQuestionService.findUserByEmailIfExists(payload.getUser().getEmail());
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
            this.clusteringFormService.submitClusteringForm(new ClusteringForm(submitter,toInsert.toArray(new Answer[toInsert.size()])));
            if (this.clusteringFormService.getSubmittedAnswers().size() > 100){
                List<ClusteredForm> clusteredData = this.clusteringQuestionService.clusterQuestions();
                this.clusteredFormRepository.saveAll(clusteredData);
            }

        }

    }
    @PostMapping(value ="/deleteQuestion", consumes="application/json", produces="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public boolean deleteQuestion(@RequestBody DeleteQuestionRequest toRemove){
        if (this.clusteringQuestionService.questionExists(toRemove.getQuestionBody())){
            this.clusteringQuestionService.deleteQuestion(toRemove.getQuestionBody());
            return true;
        }
        else {
            return false;
        }

    }
    @PostMapping(value = "/addQuestion", consumes="application/json", produces="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
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
    @CrossOrigin(origins="http://localhost:3000")
    public void populateDataset(){
        this.clusteringQuestionService.generateRandomAnswers();
    }
    @GetMapping(value ="/clusterQuestions")
    @CrossOrigin(origins="http://localhost:3000")
    public void clusterQuestions(){
        List<ClusteredForm> clusteredFormList = this.clusteringQuestionService.clusterQuestions();
        this.clusteredFormRepository.saveAll(clusteredFormList);
    }
    @GetMapping(value="/getClusteredForms")
    @CrossOrigin(origins="http://localhost:3000")
    public List<ClusteredForm> getClusteredForms(){
        return this.clusteredFormRepository.findAll();
    }

}
