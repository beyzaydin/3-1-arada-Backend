package winx.bitirme.achievement.client.model;

import winx.bitirme.sleep.service.entity.DailyStatisticEntity;

import java.util.List;

public class MobileSleepResponse {
    private List<DailyStatisticEntity> list;

    public List<DailyStatisticEntity> getList() {
        return list;
    }

    public void setList(List<DailyStatisticEntity> list) {
        this.list = list;
    }
}
