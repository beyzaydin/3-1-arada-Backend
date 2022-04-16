package winx.bitirme.mongo.service.logic;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.mongo.service.entity.ClusteringForm;
import winx.bitirme.mongo.service.repository.ClusteringFormRepository;

import java.util.List;
import java.util.Objects;

@Component
public class ClusteringFormService {
    private final ClusteringFormRepository clusteringFormRepository;

    public ClusteringFormService(ClusteringFormRepository clusteringFormRepository) {
        this.clusteringFormRepository = clusteringFormRepository;
    }

    public void submitClusteringForm(ClusteringForm toSubmit){
        this.clusteringFormRepository.save(toSubmit);
    }
    public List<ClusteringForm> getSubmittedAnswers(){
        return this.clusteringFormRepository.findAll();
    }
    public boolean didUserCompleteForm(User toTest){
        List<ClusteringForm> allData = this.getSubmittedAnswers();
        for (ClusteringForm candidate : allData){
            if (Objects.equals(candidate.getSubmitter().getId(), toTest.getId())){
                return true;
            }
        }
        return false;
    }
}
