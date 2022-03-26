package winx.bitirme.mongo.service.logic;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import winx.bitirme.auth.service.entity.EnumGender;
import winx.bitirme.auth.service.entity.Role;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.mongo.service.entity.*;
import winx.bitirme.mongo.service.repository.ClusteringQuestionRepository;

import java.util.*;

@Component
public class ClusteringQuestionService {
    ClusteringQuestionRepository clusteringQuestionRepository;
    UserRepository userRepository;
    private PasswordEncoder encoder;
    private SequenceGeneratorService sequenceGeneratorService;
    private final ClusteringFormService clusteringFormService;
    @Autowired
    public ClusteringQuestionService(ClusteringQuestionRepository clusteringQuestionRepository, UserRepository userRepository, PasswordEncoder encoder, SequenceGeneratorService sequenceGeneratorService, ClusteringFormService clusteringFormService){
        this.clusteringQuestionRepository = clusteringQuestionRepository;
        this.encoder = encoder;
        this.sequenceGeneratorService = sequenceGeneratorService;
        //Since questions are uncertain in the development process,
        //some dummy questions are constructed just to verify functionality
        if (this.clusteringQuestionRepository.findAll().isEmpty()){
            String[] dropDown = {"Completely Agree","Agree","Nor Agree Nor Disagree","Disagree","Completely Disagree"};
            List<ClusteringQuestion> toInsertInitially = new ArrayList<>();
            toInsertInitially.add(new ClusteringQuestion("I am the life of the party.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I feel little concern for others.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am always prepared.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I get stressed out easily.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I have a rich vocabulary.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I don't talk a lot.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am interested in people.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I leave my belongings around.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am relaxed most of the time.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I have difficulty understanding abstract ideas.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I feel comfortable around people.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I insult people.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I pay attention to details.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I worry about things.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I have a vivid imagination.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I keep in the background.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I sympathize with others' feelings.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I make a mess of things.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I seldom feel blue.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am not interested in abstract ideas.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I start conversations.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am not interested in other people's problems.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I get chores done right away.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am easily disturbed.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I have excellent ideas.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I have little to say.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I have a soft heart.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I often forget to put things back in their proper place.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I get upset easily.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I do not have a good imagination.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I talk to a lot of different people at parties.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am not really interested in others.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I like order.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I change my mood a lot.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am quick to understand things.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I don't like to draw attention to myself.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I take time out for others.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I shirk my duties.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I have frequent mood swings.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I use difficult words.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I don't mind being the center of attention.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I feel others' emotions.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I follow a schedule.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I get irritated easily.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I spend time reflecting on things.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am quiet around strangers.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I make people feel at ease.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am exacting in my work.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I often feel blue.",QuestionType.DROPDOWN,dropDown));
            toInsertInitially.add(new ClusteringQuestion("I am full of ideas.",QuestionType.DROPDOWN,dropDown));
            this.clusteringQuestionRepository.insert(toInsertInitially);
        }
        this.userRepository = userRepository;
        this.clusteringFormService = clusteringFormService;
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
    public boolean questionExists(String questionBody){
        return this.clusteringQuestionRepository.findById(questionBody).isPresent();
    }
    public ClusteringQuestion getQuestionIfExists(String questionBody){
        Optional<ClusteringQuestion> optionalClusteringQuestion = this.clusteringQuestionRepository.findById(questionBody);
        ClusteringQuestion result = optionalClusteringQuestion.isPresent() ? optionalClusteringQuestion.get() : null;
        return result;
    }
    public User findUserByEmailIfExists(String email){
        return this.userRepository.findByEmail(email);
    }
    public String generateRandomString(){
        String allowedChars="ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        int length = (int)(Math.random()* allowedChars.length());
        StringBuilder result =  new StringBuilder();
        int randomLocation;
        for (int i = 0; i < length; i++){
            randomLocation = (int)(Math.random()* allowedChars.length());
            result.append(allowedChars.charAt(randomLocation));
        }
        return result.toString();
    }
    public User generateNewUser(){
        String email = this.generateRandomString()+"@gmail.com";
        String name = this.generateRandomString();
        String surname = this.generateRandomString();
        String password = "12345678";
        User user = new User(email,name,surname,new Date(System.currentTimeMillis()),email,encoder.encode(password), EnumGender.FEMALE);
        long id = sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME);
        user.setId(id);
        userRepository.save(user);
        return user;
    }
    public void generateRandomAnswers(){
        Random answerPicker = new Random();
        ClusteringQuestion[] questions = this.getAllQuestions();
        int questionCount = (int)(Math.random()* 1000);
        ClusteringForm[] forms = new ClusteringForm[questionCount];
        Answer[] answer;
        int randomAnswer;
        ClusteringQuestion iterate;
        User randomUser;
        for (int i = 0; i < questionCount; i++){
            answer = new Answer[questions.length];
            for (int y=0; y < questions.length; y++){
                iterate = questions[y];
                randomAnswer = answerPicker.nextInt(iterate.getPotentialAnswer().length);
                answer[y] = new Answer(questions[y],questions[y].getPotentialAnswer()[randomAnswer]);
            }
            randomUser = this.generateNewUser();
            forms[i] = new ClusteringForm(randomUser,answer);
        }
        for ( ClusteringForm form : forms){
            this.clusteringFormService.submitClusteringForm(form);
        }
    }
    public List<ClusteredForm> clusterQuestions(){
        List<ClusteringForm> toCluster =   this.clusteringFormService.getSubmittedAnswers();
        KMeansPlusPlusClusterer<ClusteringForm> clusterer = new KMeansPlusPlusClusterer<>(5);
        List<ClusteredForm> clusteredForms = new ArrayList<>();
        int group = 1;
        List<CentroidCluster<ClusteringForm>> result = clusterer.cluster(toCluster);
        List<ClusteringForm> eachGroup;
        for (CentroidCluster cluster : result){
            eachGroup = cluster.getPoints();
            for (ClusteringForm form :eachGroup){
                clusteredForms.add(new ClusteredForm(group,form.getId(),form.getPoint()));
            }
            group++;
        }
        return clusteredForms;
    }
}
