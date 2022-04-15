package winx.bitirme.sleep.client.model;

import java.util.ArrayList;

public class SleepDataRequest {
    private String username;
    private ArrayList<SleepStatisticModel> model;

    public String getUsername() {
        return username;
    }

    public void setUsername(String
                                    username) {
        this.username = username;
    }

    public ArrayList<SleepStatisticModel> getModel() {
        return model;
    }

    public void setModel(ArrayList<SleepStatisticModel> model) {
        this.model = model;
    }

}
