package winx.bitirme.sleep.client.model;

import winx.bitirme.sleep.service.entity.DailyStatisticEntity;

import java.util.ArrayList;
import java.util.List;

public class SleepWebResponse {
    private List<DailyStatisticEntity> list;
    private List<Boolean> validDays = new ArrayList<>(7);

    public List<DailyStatisticEntity> getList() {
        return list;
    }

    public void setList(List<DailyStatisticEntity> list) {
        this.list = list;
    }

    public List<Boolean> getValidDays() {
        return validDays;
    }

    public void setValidDays(List<Boolean> validDays) {
        this.validDays = validDays;
    }
}
