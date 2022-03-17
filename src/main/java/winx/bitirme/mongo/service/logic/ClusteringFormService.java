package winx.bitirme.mongo.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import winx.bitirme.mongo.service.entity.ClusteringForm;
import winx.bitirme.mongo.service.repository.ClusteringFormRepository;
@Component
public class ClusteringFormService {
    private final ClusteringFormRepository clusteringFormRepository;

    public ClusteringFormService(ClusteringFormRepository clusteringFormRepository) {
        this.clusteringFormRepository = clusteringFormRepository;
    }

    public void submitClusteringForm(ClusteringForm toSubmit){
        this.clusteringFormRepository.save(toSubmit);
    }
}
