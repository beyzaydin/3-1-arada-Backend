package winx.bitirme.mongo.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import winx.bitirme.mongo.client.model.AnswerSubmitRequest;
import winx.bitirme.mongo.client.model.QuestionResponse;

import winx.bitirme.mongo.service.logic.AnswerService;
import winx.bitirme.mongo.service.logic.ClusteringQuestionService;


@RestController
@RequestMapping(value = "/question")
public class ClusterQuestionController {
    final ClusteringQuestionService clusteringQuestionService;
    final AnswerService answerService;
    final ObjectMapper mapper;
    @Autowired
    public ClusterQuestionController(ClusteringQuestionService clusteringQuestionService,AnswerService answerService, ObjectMapper mapper) {
        this.clusteringQuestionService = clusteringQuestionService;
        this.answerService = answerService;
        this.mapper = mapper;
    }
    @GetMapping(value = "/getQuestions", produces ="application/json")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public QuestionResponse getQuestions() {
        QuestionResponse toReturn =  new QuestionResponse(this.clusteringQuestionService.getAllQuestions());
        return toReturn;
    }
    @PostMapping(value = "/submitAnswers",consumes="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public void submitAnswers(@RequestBody AnswerSubmitRequest payload){
        this.answerService.submitAnswers(payload.getPaylaod());
    }
}