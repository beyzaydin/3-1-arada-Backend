package winx.bitirme.mongo.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class ClusteredForm {
    @Id
    private int id;
    private int group;
    private double[] locations;
    public ClusteredForm(){

    }
    public ClusteredForm(int group, int id, double[] locations){
        this.group = group;
        this.id = id;
        this.locations = locations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public double[] getLocations() {
        return locations;
    }

    public void setLocations(double[] locations) {
        this.locations = locations;
    }
}
